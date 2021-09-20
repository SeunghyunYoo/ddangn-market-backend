package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/**
 * @author SeunghyunYoo
 */
@Data
@NoArgsConstructor
public class GetMessageResponseDto {

    private List<MessageDto> messages;

    public GetMessageResponseDto(List<ChatMessage> chatMessages){
        this.messages = chatMessages.stream().map(MessageDto::new).collect(toList());
    }

    @Data
    @NoArgsConstructor
    public static class MessageDto {
        private MessageType messageType;
        private String sender;
        private String message;
        private String createdAt;
        private String roomId;
        private int unreadCount;

        public MessageDto(ChatMessage chatMessage){
            this.messageType = chatMessage.getMessageType();
            this.sender = chatMessage.getSender();
            this.message = chatMessage.getMessage();
            this.roomId = chatMessage.getRoomId();
            this.unreadCount = chatMessage.getUnreadCount();
//            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//            this.createdAt = dateFormat.format(chatMessage.getCreatedAt());
            int hour = LocalDateTime.from(chatMessage.getCreatedAt()).getHour();
            int minute = LocalDateTime.from(chatMessage.getCreatedAt()).getMinute();
            int second = LocalDateTime.from(chatMessage.getCreatedAt()).getSecond();
            this.createdAt = String.format("%02d:%02d:%02d", hour, minute, second);
        }
    }
}
