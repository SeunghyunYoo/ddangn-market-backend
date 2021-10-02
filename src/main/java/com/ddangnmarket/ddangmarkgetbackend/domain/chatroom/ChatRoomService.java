package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author SeunghyunYoo
 */
@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;

    public ChatRoom createChatRoom(){
        ChatRoom chatRoom = ChatRoom.creteChatRoom();
        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);
//        chatRoomRedisRepository.save(saveChatRoom);
        return saveChatRoom;
    }

    public List<ChatRoom> findAllChatRooms(){
        //TODO redis, jpa 중 어디서 처리해야 하는지 고민 필요
//        return chatRoomRedisRepository.findAll();
        return chatRoomRepository.findAll();
    }


    public ChatRoom findChatRoom(String roomId){
        //TODO redis, jpa 중 어디서 처리해야 하는지 고민 필요 -> redis 저장소 필요 없음
//        return chatRoomRedisRepository.findChatRoomById(roomId).orElseThrow();
        return chatRoomRepository.findByRoomId(roomId).orElseThrow();
    }

    public Long getUserCount(String roomId){
        return chatRoomRedisRepository.getConnectionCount(roomId);
    }


    public void increaseConnection(String sessionId, String roomId){
        Optional<String> roomIdFromSession = chatRoomRedisRepository.getRoomIdFromSession(sessionId);
        if(roomIdFromSession.isEmpty()){
            chatRoomRedisRepository.addUserEnterInfo(sessionId, roomId);
            chatRoomRedisRepository.plusConnectionCount(roomId);
            return;
        } else if (!roomIdFromSession.get().equals(roomId)){
            chatRoomRedisRepository.plusConnectionCount(roomId);
        }
        chatRoomRedisRepository.addUserEnterInfo(sessionId, roomId);
    }

    public void decreaseConnection(String sessionId){
        if(chatRoomRedisRepository.getRoomIdFromSession(sessionId).isPresent()){
            String roomId = chatRoomRedisRepository.getRoomIdFromSession(sessionId).get();
            chatRoomRedisRepository.minusConnectionCount(roomId);
            chatRoomRedisRepository.removeUserEnterInfo(sessionId);
        }
    }

    public void increaseConnection2(String loginId, String sessionId, String roomId){
//        Optional<String> optRoomId = chatRoomRedisRepository.getRoomIdFromMap(loginId, sessionId);
//        if(optRoomId.isEmpty()){
        if(!chatRoomRedisRepository.getInfoMap(loginId).containsValue(roomId)){
            chatRoomRedisRepository.addUserEnterInfo2(loginId, sessionId, roomId);
            chatRoomRedisRepository.plusConnectionCount(roomId);
            return;
        }
//        else if (!optRoomId.get().equals(roomId)){
//            chatRoomRedisRepository.plusConnectionCount(roomId);
//        }
//        chatRoomRedisRepository.plusConnectionCount(roomId);
        chatRoomRedisRepository.addUserEnterInfo2(loginId, sessionId, roomId);
    }

    public void decreaseConnection2(String loginId, String sessionId){
        String roomId = chatRoomRedisRepository.getRoomIdFromMap(loginId, sessionId).orElseThrow();
//        Map<String, String> map = chatRoomRedisRepository.getSessionRoomMapFromLoginId(loginId);
        // map에서 안지워지는듯 함
//        map.remove(sessionId);
        chatRoomRedisRepository.removeUserEnterInfo2(loginId, sessionId);
        if(!chatRoomRedisRepository.getInfoMap(loginId).containsValue(roomId)){
            chatRoomRedisRepository.minusConnectionCount(roomId);
        }
    }

    public void decreaseConnectionIfRoomExist(String loginId, String sessionId){
        Optional<String> optRoomId = chatRoomRedisRepository.getRoomIdFromMap(loginId, sessionId);
        if(optRoomId.isEmpty()){
            return;
        }
//        Map<String, String> map = chatRoomRedisRepository.getSessionRoomMapFromLoginId(loginId);
        // map에서 안지워지는듯 함
//        map.remove(sessionId);
        chatRoomRedisRepository.removeUserEnterInfo2(loginId, sessionId);
        if(!chatRoomRedisRepository.getInfoMap(loginId).containsValue(optRoomId.get())){
            chatRoomRedisRepository.minusConnectionCount(optRoomId.get());
        }
    }
}
