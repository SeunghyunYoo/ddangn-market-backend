package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateChatResponseDto {
    private Long chatId;
    public CreateChatResponseDto(Long chatId){
        this.chatId = chatId;
    }
}
