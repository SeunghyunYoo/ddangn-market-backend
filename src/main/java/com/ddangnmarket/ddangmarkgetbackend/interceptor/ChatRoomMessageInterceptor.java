package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessageService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomRedisRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomService;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.ddangnmarket.ddangmarkgetbackend.config.redis.SubscriptionIdConst.CHAT_ROOM;

/**
 * @author SeunghyunYoo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ChatRoomMessageInterceptor implements ChannelInterceptor {


    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    // TODO jwt 토큰으로 인증처리 하거나,
    //  header 값을 통해 처리할 것이 있으면 적용
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String loginAccountId = getLoginAccountId(message);
        if (StompCommand.DISCONNECT.equals(accessor.getCommand())){
            String simpSessionId = getSimpSessionId(message);
            chatRoomService.decreaseConnectionIfRoomExist(loginAccountId, simpSessionId);
        }
        if(!checkIsChatRoomMessageSubscription(message)){
            return message;
        }
        if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            // subscription Id를 js로 부터 받도록 설정
            String simpSessionId = getSimpSessionId(message);
            String roomId = getRoomId(message);
//            chatRoomService.increaseConnection(simpSessionId, roomId);
            chatRoomService.increaseConnection2(loginAccountId, simpSessionId, roomId);
            log.info("SUBSCRIBE");
        } else if(StompCommand.UNSUBSCRIBE.equals(accessor.getCommand())){
            String simpSessionId = getSimpSessionId(message);
//            chatRoomService.decreaseConnection(simpSessionId);
            chatRoomService.decreaseConnection2(loginAccountId, simpSessionId);
        }
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

    private boolean checkDestination(Message<?> message){
        return getSimpDestination(message).contains("/sub/api/chatroom/");
    }

    public String getSimpDestination(Message<?> message){
        Object simpDestination = message.getHeaders().get("simpDestination");
        if(simpDestination == null){
            return "";
        }
        return (String) simpDestination;
    }

    private boolean checkIsChatRoomMessageSubscription(Message<?> message){
        Object simpSubscriptionId = message.getHeaders().get("simpSubscriptionId");
        if(simpSubscriptionId == null) {
            return false;
        }
        return simpSubscriptionId.equals(CHAT_ROOM.getSubId());
    }

    private String getSimpSessionId(Message<?> message){
        return (String) message.getHeaders().get("simpSessionId");
    }


    private String getRoomId(Message<?> message) {
        String simpDestination = getSimpDestination(message);
        int index = simpDestination.lastIndexOf("/");
        return simpDestination.substring(index + 1);
    }

}
