package com.ddangnmarket.ddangmarkgetbackend.config;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostControllerV2;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.BeanDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerBase;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.*;
import java.io.IOException;
import java.util.Set;

@Configuration(proxyBeanMethods = false)
public class AppConfig {

//    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.getConfiguration()
//                .setDestinationNameTokenizer(NameTokenizers.UNDERSCORE)
//                .setSourceNameTokenizer(NameTokenizers.UNDERSCORE);
        return modelMapper;
    }

    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();

        SimpleModule validationModule = new SimpleModule();
        validationModule.setDeserializerModifier(new BeanDeserializerModifier() {
            @Override
            public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
                if (deserializer instanceof BeanDeserializer) {
                    return new BeanValidationDeserializer((BeanDeserializer) deserializer);
                }
                return deserializer;
            }
        });
        objectMapper.registerModule(validationModule);

        return objectMapper;


    }

    public static class BeanValidationDeserializer extends BeanDeserializer {
        private final static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        private final Validator validator = factory.getValidator();
        public BeanValidationDeserializer(BeanDeserializerBase src) {
            super(src);
        }
        @Override
        public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            Object instance = super.deserialize(p, ctxt);
            validate(instance);
            return instance;
        }
        private void validate(Object instance) {
            Set<ConstraintViolation<Object>> violations = validator.validate(instance);
            if (violations.size() > 0) {
                StringBuilder msg = new StringBuilder();
                msg.append("JSON object is not valid. Reasons (").append(violations.size()).append("): ");
                for (ConstraintViolation<Object> violation : violations) {
                    msg.append(violation.getMessage()).append(", ");
                }
                throw new ConstraintViolationException(msg.toString(), violations);
            }
        }
    }



}