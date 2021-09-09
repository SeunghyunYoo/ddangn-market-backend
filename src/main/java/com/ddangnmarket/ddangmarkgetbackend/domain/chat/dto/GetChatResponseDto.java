package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChatResponseDto {
    private Long chatId;
    private String sellerNickname;
    private String buyerNickname;
    private PostStatus postStatus;
    private String createdAt;
    private String updatedAt;

    public GetChatResponseDto(Chat chat){
        this.chatId = chat.getId();
        this.sellerNickname = chat.getPost().getSeller().getNickname();
        this.buyerNickname = chat.getAccount().getNickname();
        this.postStatus = chat.getPost().getPostStatus();
        this.createdAt = chat.getCreatedAt().toString();
        this.updatedAt = chat.getUpdatedAt().toString();
    }
}
