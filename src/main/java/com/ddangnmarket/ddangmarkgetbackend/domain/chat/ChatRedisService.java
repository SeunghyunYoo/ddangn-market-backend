package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.TopicConst;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessage;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessageRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.ChatMessageSubDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.MessageType;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author SeunghyunYoo
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatRedisService {

    private final int TWO_PEOPLE_MESSAGE_ACCOUNT_COUNT = 2;

    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final EnterInfoRepository enterInfoRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ChannelTopic> topics;

    public void sendMessage(ChatMessage chatMessage){
        String roomId = chatMessage.getRoomId();
        String sender = chatMessage.getSender();

        Long connectionCount = chatRoomRedisRepository.getConnectionCount(roomId);

        if(MessageType.ENTER.equals(chatMessage.getMessageType())){
            if(is1stEnter(sender, roomId)){
                chatMessage.setMessageType(MessageType.NOTIFICATION_1ST_ENTER);
                chatMessage.setMessage(MessageType.NOTIFICATION_1ST_ENTER.name());
                ChatMessageSubDto chatMessageSubDto =
                        new ChatMessageSubDto(chatMessage, TWO_PEOPLE_MESSAGE_ACCOUNT_COUNT, connectionCount);
                redisTemplate.convertAndSend(topics.get(TopicConst.CHAT_ROOM).getTopic(), chatMessageSubDto);
                chatMessageRepository.save(chatMessage);
                return;
            }
            chatMessage.setMessageType(MessageType.NOTIFICATION_ENTER);
            chatMessage.setMessage(MessageType.NOTIFICATION_ENTER.name());
            ChatMessageSubDto chatMessageSubDto =
                    new ChatMessageSubDto(chatMessage, TWO_PEOPLE_MESSAGE_ACCOUNT_COUNT, connectionCount);

//            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();

//            List<EnterInfo> enterInfos = chatRoom.getEnterInfos();
            resetUnreadCount(roomId, sender);

            redisTemplate.convertAndSend(topics.get(TopicConst.CHAT_ROOM).getTopic(), chatMessageSubDto);
        } else if(MessageType.TALK.equals(chatMessage.getMessageType())){
            ChatMessageSubDto chatMessageSubDto =
                    new ChatMessageSubDto(chatMessage, TWO_PEOPLE_MESSAGE_ACCOUNT_COUNT, connectionCount);
            redisTemplate.convertAndSend(topics.get(TopicConst.CHAT_ROOM).getTopic(), chatMessageSubDto);


            // 해당 채팅에 참여한 유저들에게 notification 메세지
            // -> 그러면 sub url에 닉네임이 들어가야 할듯
            // 채팅방에 unread Count 갱신
//            ChatRoom chatRoom = chatRoomRepository.findByRoomId(roomId).orElseThrow();
//            List<EnterInfo> enterInfos = chatRoom.getEnterInfos();
            increaseUnreadCount(roomId, sender);
//            resetUnreadCount(roomId, sender);

            redisTemplate.convertAndSend(topics.get(TopicConst.NOTIFICATION_MESSAGE).getTopic(), chatMessageSubDto);
            chatMessageRepository.save(chatMessage);
        }
    }

    private void resetUnreadCount(String roomId, String sender) {
        List<EnterInfo> enterInfos = enterInfoRepository.findByRoomId(roomId);

        List<EnterInfo> collect = enterInfos.stream()
                .filter(enterInfo ->
                        enterInfo.getAccount().getNickname().equals(sender)
                ).collect(Collectors.toList());
        collect.forEach(EnterInfo::resetUnreadCount);
    }

    private void increaseUnreadCount(String roomId, String sender) {

        if(chatRoomRedisRepository.getConnectionCount(roomId).equals(2L)){
            return;
        }
        List<EnterInfo> enterInfos = enterInfoRepository.findByRoomId(roomId);

        List<EnterInfo> collect = enterInfos.stream()
                .filter(enterInfo ->
                        !enterInfo.getAccount().getNickname().equals(sender)
                ).collect(Collectors.toList());
        collect.forEach(EnterInfo::increaseUnreadCount);

        List<EnterInfo> collect2 = enterInfos.stream()
                .filter(enterInfo ->
                        enterInfo.getAccount().getNickname().equals(sender)
                ).collect(Collectors.toList());
        collect2.forEach(EnterInfo::resetUnreadCount);
    }


    private boolean is1stEnter(String sender, String roomId){
        return !chatMessageRepository.existsBySenderAndRoomId(sender, roomId);
    }
}
