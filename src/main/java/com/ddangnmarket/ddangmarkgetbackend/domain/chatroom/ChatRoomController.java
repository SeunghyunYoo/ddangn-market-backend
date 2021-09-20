package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;
    @PostMapping
    public ChatRoom createChatRoom(){
        ChatRoom chatRoom = chatRoomService.createChatRoom();
        return chatRoom;
    }

    @GetMapping
    public List<ChatRoom> getAllChatRoom(){
        List<ChatRoom> chatRooms = chatRoomService.findAllChatRooms();
        return chatRooms;
    }

    @GetMapping("/roomId")
    public ChatRoom findChatRoom(@RequestParam String roomId){
        return chatRoomService.findChatRoom(roomId);
    }
}
