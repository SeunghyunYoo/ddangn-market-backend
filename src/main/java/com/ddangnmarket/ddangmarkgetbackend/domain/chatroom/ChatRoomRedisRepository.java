package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author SeunghyunYoo
 */
@Repository
@RequiredArgsConstructor
public class ChatRoomRedisRepository {

    private final String CHAT_ROOM = "CHAT_ROOM";

    private final RedisTemplate<String, Object> redisTemplate;

    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
//    private Map<String, ChannelTopic> topics;

    @PostConstruct
    private void init(){
        opsHashChatRoom = redisTemplate.opsForHash();
//        topics = new HashMap<>();
    }

    public List<ChatRoom> findAll(){
        return opsHashChatRoom.values(CHAT_ROOM);
    }

    public Optional<ChatRoom> findChatRoomById(String id){
        return Optional.ofNullable(opsHashChatRoom.get(CHAT_ROOM, id));
    }

    public ChatRoom create(){
        ChatRoom chatRoom = ChatRoom.creteChatRoom();
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);
        return chatRoom;
    }

    public void save(ChatRoom chatRoom){
        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);
    }

}
