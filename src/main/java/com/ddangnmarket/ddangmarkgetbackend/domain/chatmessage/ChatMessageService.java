package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * @author SeunghyunYoo
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> findAllMessage(String sender, String roomId){
        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMsgByRoomId(sender, roomId);
        unreadMessages.forEach(ChatMessage::decreaseUnreadCount);

        return chatMessageRepository.findAllByRoomIdOrderByCreatedAtAsc(roomId);
    }



    public void saveMessage(ChatMessage chatMessage) {
//        if (chatMessage.getMessageType() == MessageType.TALK) {
        chatMessageRepository.save(chatMessage);
//        }
    }

    public void unreadToRead(String roomId, String sender){
        List<ChatMessage> unreadMessages = chatMessageRepository.findUnreadMsgByRoomId(sender, roomId);
        unreadMessages.forEach(ChatMessage::decreaseUnreadCount);
    }
}
