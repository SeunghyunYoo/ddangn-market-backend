package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChatResponseDto {
    private String sellerNickname;
    private String buyerNickname;
    private String createdAt;
    private String updatedAt;

    public GetChatResponseDto(Chat chat){
        this.sellerNickname = chat.getPost().getSeller().getNickname();
        this.buyerNickname = chat.getAccount().getNickname();
        this.createdAt = chat.getCreatedAt().toString();
        this.updatedAt = chat.getUpdatedAt().toString();
    }
}
