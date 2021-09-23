package com.ddangnmarket.ddangmarkgetbackend.config;

import com.ddangnmarket.ddangmarkgetbackend.interceptor.ChatRoomMessageInterceptor;
import com.ddangnmarket.ddangmarkgetbackend.interceptor.NotificationMessageInterceptor;
import com.ddangnmarket.ddangmarkgetbackend.interceptor.SubscriptionCheckInterceptor;
import com.ddangnmarket.ddangmarkgetbackend.interceptor.WebSocketHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.util.PathMatcher;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author SeunghyunYoo
 */
@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final ChatRoomMessageInterceptor chatRoomMessageInterceptor;
    private final SubscriptionCheckInterceptor subscriptionCheckInterceptor;
    private final NotificationMessageInterceptor notificationMessageInterceptor;
    private final WebSocketHandshakeInterceptor webSocketHandshakeInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/sub");
        registry.setApplicationDestinationPrefixes("/pub");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws-stomp")
                .setAllowedOrigins("http://localhost:3000")
                .addInterceptors(webSocketHandshakeInterceptor)
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(
                subscriptionCheckInterceptor,
                notificationMessageInterceptor,
                chatRoomMessageInterceptor);
    }


}
