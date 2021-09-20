package com.ddangnmarket.ddangmarkgetbackend.interceptor;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * @author SeunghyunYoo
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ChatRoomMessageInterceptor implements ChannelInterceptor {

    private final ChatRoomService chatRoomService;
    // TODO jwt 토큰으로 인증처리 하거나,
    //  header 값을 통해 처리할 것이 있으면 적용
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())){
            log.info("CONNECT");
        } else if (StompCommand.SEND.equals(accessor.getCommand())){
            log.info("SEND");
        } else if (StompCommand.SUBSCRIBE.equals(accessor.getCommand())){
            log.info("SUBSCRIBE");
        }
        return message;


    }

}
