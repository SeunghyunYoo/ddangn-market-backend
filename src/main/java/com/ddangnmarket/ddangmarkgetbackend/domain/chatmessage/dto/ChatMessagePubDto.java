package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author SeunghyunYoo
 */
@NoArgsConstructor
@Data
public class ChatMessagePubDto {
    private MessageType messageType;
    private String message;
    private String roomId;
}
