package com.ddangnmarket.ddangmarkgetbackend.config.redis;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.ChatMessageSubDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

/**
 * @author SeunghyunYoo
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SimpMessageSendingOperations messageTemplate;

    public void sendMessage(String publishMessage){
        try {
            ChatMessageSubDto chatMessage =
                    objectMapper.readValue(publishMessage, ChatMessageSubDto.class);
            messageTemplate.convertAndSend(
                    "/sub/api/chatroom/" + chatMessage.getRoomId(), chatMessage);

        } catch (JsonProcessingException e) {
            log.error("ChatMessage Exception", e);
            throw new RuntimeException("chatMessage exception");
        }

    }

}
