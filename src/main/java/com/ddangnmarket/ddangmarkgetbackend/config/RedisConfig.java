package com.ddangnmarket.ddangmarkgetbackend.config;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.RedisSubscriber;
import com.ddangnmarket.ddangmarkgetbackend.config.redis.TopicConst;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
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
        topics.put(TopicConst.NOTIFICATION_MESSAGE, new ChannelTopic(TopicConst.NOTIFICATION_MESSAGE));
        topics.put(TopicConst.NOTIFICATION_POST, new ChannelTopic(TopicConst.NOTIFICATION_POST));
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
//        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
//        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    RedisTemplate<String, Long> redisLongTemplate(){
        RedisTemplate<String, Long> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericToStringSerializer<>(Long.class));
        redisTemplate.setHashValueSerializer(new GenericToStringSerializer<>(Long.class));
        return redisTemplate;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(
            RedisConnectionFactory redisConnectionFactory,
            @Qualifier("messageListenerAdapter") MessageListenerAdapter messageListenerAdapter,
            @Qualifier("messageNotificationListenerAdapter") MessageListenerAdapter messageNotificationListenerAdapter,
            @Qualifier("notificationPostListenerAdapter") MessageListenerAdapter notificationPostListenerAdapter,
            Map<String, ChannelTopic> topics
            ){
//            ChannelTopic channelTopic){
        // TODO 여러개 타픽으로 설정하고자 하는경우 변경 필요해 보임
        //  ex> notification을 위한 topic, 게시글 댓글 실시간 적용
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, topics.get(TopicConst.CHAT_ROOM));
        container.addMessageListener(messageNotificationListenerAdapter, topics.get(TopicConst.NOTIFICATION_MESSAGE));
        container.addMessageListener(notificationPostListenerAdapter, topics.get(TopicConst.NOTIFICATION_POST));

        return container;
    }

    @Bean("messageListenerAdapter")
    public MessageListenerAdapter messageListenerAdapter(RedisSubscriber redisSubscriber){
        return new MessageListenerAdapter(redisSubscriber, "sendMessage");
    }

    @Bean("messageNotificationListenerAdapter")
    public MessageListenerAdapter messageNotificationListenerAdapter(RedisSubscriber redisSubscriber){
        return new MessageListenerAdapter(redisSubscriber, "sendMessageNotification");
    }

    @Bean("notificationPostListenerAdapter")
    public MessageListenerAdapter notificationPostListenerAdapter(RedisSubscriber redisSubscriber){
        return new MessageListenerAdapter(redisSubscriber, "sendNotificationPost");
    }
}
