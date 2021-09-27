package com.ddangnmarket.ddangmarkgetbackend.config.redis;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.ChatMessageSubDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.EnterInfo;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.EnterInfoRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SeunghyunYoo
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisSubscriber {
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final SimpMessageSendingOperations messageTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final EnterInfoRepository enterInfoRepository;

    public void sendMessage(String publishMessage){
        try {
            ChatMessageSubDto chatMessage =
                    objectMapper.readValue(publishMessage, ChatMessageSubDto.class);
            SimpMessageHeaderAccessor simpMessageHeaderAccessor = SimpMessageHeaderAccessor.create(SimpMessageType.MESSAGE);
            simpMessageHeaderAccessor.setSubscriptionId("sub-chatroom");
            messageTemplate.convertAndSend(
                    "/sub/api/chatroom/" + chatMessage.getRoomId(), chatMessage,
                    simpMessageHeaderAccessor.getMessageHeaders());

        } catch (JsonProcessingException e) {
            log.error("ChatMessage Exception", e);
            throw new RuntimeException("chatMessage exception");
        }

    }

    public void sendMessageNotification(String publishMessage, @Header("simpSessionId") String sessionId){

        try {
            ChatMessageSubDto chatMessage =
                    objectMapper.readValue(publishMessage, ChatMessageSubDto.class);

            String sender = chatMessage.getSender();
            String roomId = chatMessage.getRoomId();

            List<String> nicknames = getTargetNicknames(sender, roomId);

            // TODO send to specific users that enters chatroom
            //  simpSessionId로 처리하면 여러 브라우저에서 접속했을때 복잡해짐
            nicknames.forEach(nickname -> {
                messageTemplate.convertAndSend(
                        "/sub/api/user/" + nickname +"/notification/message", publishMessage);
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private List<String> getTargetNicknames(String sender, String roomId) {
        List<EnterInfo> enterInfos = enterInfoRepository.findByRoomId(roomId);
        List<String> nicknames = enterInfos.stream()
                .map(EnterInfo::getAccount)
                .map(Account::getNickname)
                .filter(nickname -> !nickname.equals(sender))
                .collect(Collectors.toList());
        return nicknames;
    }

    public void sendNotificationPost(String publishMessage){

    }

}
