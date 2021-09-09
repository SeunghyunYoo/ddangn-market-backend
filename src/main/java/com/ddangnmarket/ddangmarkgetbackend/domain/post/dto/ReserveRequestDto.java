package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.Data;

@Data
public class ReserveRequestDto {
    private Long chatId;
    private PostStatus postStatus;
}
