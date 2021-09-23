package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessage;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int accountCount;
    private long connectionCount;
    private String createdAt;

    public ChatMessageSubDto(ChatMessage chatMessage, int accountCount, long connectionCount){
        this.messageType = chatMessage.getMessageType();
        this.sender = chatMessage.getSender();
        this.message = chatMessage.getMessage();
        this.roomId = chatMessage.getRoomId();
        this.accountCount = accountCount;
        this.connectionCount = connectionCount;
//        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        this.createdAt = dateFormat.format(chatMessage.getCreatedAt());
        int hour = LocalDateTime.from(chatMessage.getCreatedAt()).getHour();
        int minute = LocalDateTime.from(chatMessage.getCreatedAt()).getMinute();
        int second = LocalDateTime.from(chatMessage.getCreatedAt()).getSecond();
        this.createdAt = String.format("%02d:%02d:%02d", hour, minute, second);
    }
}
