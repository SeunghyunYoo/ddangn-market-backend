package com.ddangnmarket.ddangmarkgetbackend.domain.post.event;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author SeunghyunYoo
 */
@Data
@AllArgsConstructor
public class PostSaleMessageDto {
    private MessageType messageType;
    private String message;
    private Long postId;
}
