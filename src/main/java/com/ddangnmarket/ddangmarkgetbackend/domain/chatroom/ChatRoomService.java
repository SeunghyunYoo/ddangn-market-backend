package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

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
        chatRoomRedisRepository.save(chatRoom);
        return saveChatRoom;
    }

    public List<ChatRoom> findAllChatRooms(){
        //TODO redis, jpa 중 어디서 처리해야 하는지 고민 필요
        return chatRoomRedisRepository.findAll();
    }


    public ChatRoom findChatRoom(String roomId){
        //TODO redis, jpa 중 어디서 처리해야 하는지 고민 필요
        return chatRoomRedisRepository.findChatRoomById(roomId).orElseThrow();
    }
}
