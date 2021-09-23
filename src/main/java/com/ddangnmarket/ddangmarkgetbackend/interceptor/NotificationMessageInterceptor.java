package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.SubscriptionIdConst;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

/**
 * @author SeunghyunYoo
 */
@Component
@RequiredArgsConstructor
public class NotificationMessageInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        if(!Objects.equals(message.getHeaders().get("simpDestination"), "/sub/api/notification/message")){
            if(!checkSubscriptionId(message)) {
                return message;
            }
        }

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        return message;
    }

    private String getLoginAccountId(Message<?> message){
        Object simpSessionAttributes = message.getHeaders().get("simpSessionAttributes");
        if(simpSessionAttributes instanceof Map){
            Long loginId = ((Map<?, ?>) simpSessionAttributes).entrySet().stream()
                    .filter(item -> item.getKey() == SessionConst.LOGIN_ACCOUNT && item.getValue() instanceof Long)
                    .map(item -> (Long) item.getValue())
                    .findAny()
                    .orElseThrow();
            return String.valueOf(loginId);
        }
        throw new RuntimeException("no login");
    }

    private boolean checkSubscriptionId(Message<?> message){
        Object simpSubscriptionId = message.getHeaders().get("simpSubscriptionId");
        if(simpSubscriptionId == null) {
            return false;
        }
        return simpSubscriptionId.equals(SubscriptionIdConst.NOTIFICATION_MSG.getSubId());
    }

}
