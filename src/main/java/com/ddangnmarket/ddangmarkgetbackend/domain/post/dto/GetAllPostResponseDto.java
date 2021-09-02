package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllPostResponseDto {

    private List<GetPostResponseDto> posts;

    public GetAllPostResponseDto(List<Post> posts){
        this.posts = posts.stream().map(GetPostResponseDto::new).collect(Collectors.toList());
    }
}
