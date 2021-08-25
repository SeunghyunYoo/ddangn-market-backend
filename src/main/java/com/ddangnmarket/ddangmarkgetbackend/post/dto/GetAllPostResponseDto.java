package com.ddangnmarket.ddangmarkgetbackend.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllPostResponseDto {

    private List<GetPostResponseDto> getPostResponseDtos;

    public GetAllPostResponseDto(List<Post> posts){
        this.getPostResponseDtos = posts.stream().map(GetPostResponseDto::new).collect(Collectors.toList());
    }
}
