package com.ddangnmarket.ddangmarkgetbackend.config;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.RedisSubscriber;
import com.ddangnmarket.ddangmarkgetbackend.config.redis.TopicConst;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SeunghyunYoo
 */
@Configuration
//@EnableRedisRepositories
public class RedisConfig {

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.host}")
    private String host;

    @Bean
    public Map<String, ChannelTopic> topics(){
        Map<String, ChannelTopic> topics = new HashMap<>();
        topics.put(TopicConst.CHAT_ROOM, new ChannelTopic(TopicConst.CHAT_ROOM));
        return topics;
    }


    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(){
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("messageListenerAdapter") MessageListenerAdapter messageListenerAdapter,
            Map<String, ChannelTopic> topics
            ){
//            ChannelTopic channelTopic){
        // TODO 여러개 타픽으로 설정하고자 하는경우 변경 필요해 보임
        //  ex> notification을 위한 topic, 게시글 댓글 실시간 적용
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
//        container.addMessageListener(messageListenerAdapter, channelTopic);
        container.addMessageListener(messageListenerAdapter, topics.get(TopicConst.CHAT_ROOM));
        return container;
    }



    @Bean("messageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter(RedisSubscriber redisSubscriber){
        return new MessageListenerAdapter(redisSubscriber, "sendMessage");
    }

//    @Bean
//    public ChannelTopic channelTopic(){
//        return new ChannelTopic("chatroom");
//    }
}
