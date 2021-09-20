package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

/**
 * @author SeunghyunYoo
 */
@Data
@NoArgsConstructor
public class ChatMessageSubDto {
    private MessageType messageType;
    private String sender;
    private String message;
    private String roomId;
    private int unreadCount;
    private String createdAt;

    public ChatMessageSubDto(ChatMessage chatMessage){
        this.messageType = chatMessage.getMessageType();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.roomId = chatMessage.getRoomId();
        this.unreadCount = chatMessage.getUnreadCount();
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        this.createdAt = dateFormat.format(chatMessage.getCreatedAt());
        int hour = LocalDateTime.from(chatMessage.getCreatedAt()).getHour();
        int minute = LocalDateTime.from(chatMessage.getCreatedAt()).getMinute();
        int second = LocalDateTime.from(chatMessage.getCreatedAt()).getSecond();
        this.createdAt = String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
