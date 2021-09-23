package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.RedisSubIdConst;
import com.ddangnmarket.ddangmarkgetbackend.config.redis.SubscriptionIdConst;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SeunghyunYoo
 */
@Component
public class SubscriptionCheckInterceptor implements ChannelInterceptor {
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if(StompCommand.SUBSCRIBE.equals(accessor.getCommand()) ||
                StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            validateSubscriptionId(message);
        }
        return message;
    }

    private void validateSubscriptionId(Message<?> message) {
        String simpSubscriptionId = getSimpSubscriptionId(message);

        SubscriptionIdConst[] values = SubscriptionIdConst.values();
        boolean validated = Arrays.stream(values).map(SubscriptionIdConst::getSubId).anyMatch(
                id -> id.equals(simpSubscriptionId));

        if(!validated){
            throw new IllegalArgumentException("invalid subscriptionId");
        }
    }

    private String getSimpSubscriptionId(Message<?> message){
        Object simpSubscriptionId = message.getHeaders().get("simpSubscriptionId");
        if(simpSubscriptionId == null){
            throw new IllegalArgumentException("there is no subscription id in header");
        }
        return (String) simpSubscriptionId;
    }
}
