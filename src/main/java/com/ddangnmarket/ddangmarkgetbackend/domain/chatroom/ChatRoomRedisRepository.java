package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SeunghyunYoo
 */
@Repository
@RequiredArgsConstructor
public class ChatRoomRedisRepository {

    private final String CHAT_ROOM = "CHAT_ROOM";
    private final String ACCOUNT_COUNT = "ACCOUNT_COUNT";
    private final String CONNECTION_COUNT = "CONNECTION_COUNT";
    private final String ENTER_INFO = "ENTER_INFO";
    private final String ENTER_INFO2 = "ENTER_INFO2";

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, Long> redisLongTemplate;

//    private HashOperations<String, String, ChatRoom> opsHashChatRoom;
    private HashOperations<String, String, String> opsHashEnterInfo;
    private HashOperations<String, String, String> opsHashEnterInfo2;
    private HashOperations<String, String, Long> opsHashConnectionCount;

    @PostConstruct
    private void init(){
//        opsHashChatRoom = redisTemplate.opsForHash();
        opsHashEnterInfo = redisTemplate.opsForHash();
        opsHashEnterInfo2 = redisTemplate.opsForHash();
        opsHashConnectionCount = redisLongTemplate.opsForHash();
    }

//    public List<ChatRoom> findAll(){
//        return opsHashChatRoom.values(CHAT_ROOM);
//    }
//
//    public Optional<ChatRoom> findChatRoomById(String id){
//        return Optional.ofNullable(opsHashChatRoom.get(CHAT_ROOM, id));
//    }
//
//    public ChatRoom create(){
//        ChatRoom chatRoom = ChatRoom.creteChatRoom();
//        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }

    public void save(ChatRoom chatRoom){
//        opsHashChatRoom.put(CHAT_ROOM, chatRoom.getRoomId(), chatRoom);
        opsHashConnectionCount.put(CONNECTION_COUNT, chatRoom.getRoomId(), 0L);
    }

    public void addUserEnterInfo(String sessionId, String roomId){
        opsHashEnterInfo.put(ENTER_INFO, sessionId, roomId);
    }

    public Optional<String> getRoomIdFromMap(String loginId, String sessionId){
        return Optional.ofNullable(opsHashEnterInfo2.get(ENTER_INFO2+loginId, sessionId));
    }

    public Map<String, String> getInfoMap(String loginId){
        return opsHashEnterInfo2.entries(ENTER_INFO2 + loginId);
    }

    public void addUserEnterInfo2(String loginId, String sessionId, String roomId){
        opsHashEnterInfo2.put(ENTER_INFO2+loginId, sessionId, roomId);
    }

    public void removeUserEnterInfo2(String loginId, String sessionId){
        opsHashEnterInfo2.delete(ENTER_INFO2+loginId, sessionId);
    }

    public void removeUserEnterInfo(String sessionId) {
        opsHashEnterInfo.delete(ENTER_INFO, sessionId);
    }

    public Optional<String> getRoomIdFromSession(String sessionId){
       return Optional.ofNullable(opsHashEnterInfo.get(ENTER_INFO, sessionId));
    }

    public Long getConnectionCount(String roomId){
        return Optional.ofNullable(opsHashConnectionCount.get(CONNECTION_COUNT, roomId)).orElse(0L);
    }

    public Long plusConnectionCount(String roomId){
        return opsHashConnectionCount.increment(CONNECTION_COUNT, roomId, 1L);
    }

    public Long minusConnectionCount(String roomId){
        return opsHashConnectionCount.increment(CONNECTION_COUNT, roomId, -1L);
    }

}
