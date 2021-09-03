package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetAllPostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetAllChatResponseDto {
    private List<GetChatResponseDto> chats;

    public GetAllChatResponseDto(List<Chat> chats){
        this.chats = chats.stream().map(GetChatResponseDto::new).collect(Collectors.toList());
    }
}
