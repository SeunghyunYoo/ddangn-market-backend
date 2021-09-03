package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.ChatStatus;
import lombok.Data;

@Data
public class ReserveRequestDto {
    private Long chatId;
    private ChatStatus chatStatus;
}
