package com.ddangnmarket.ddangmarkgetbackend.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.NameTokenizers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

//    @Bean
    public ObjectMapper objectMapper(){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper;
    }

}q