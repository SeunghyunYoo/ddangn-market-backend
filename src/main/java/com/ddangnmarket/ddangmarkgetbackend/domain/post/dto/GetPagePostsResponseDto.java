package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetPagePostsResponseDto {

    private List<GetPostResponseDto> posts;
    private int currentPage;
    private boolean isFirst;
    private boolean hasNext;
    private int totalPages;
    private long totalElements;

    public GetPagePostsResponseDto(Page<Post> pagePost){
        List<Post> posts = pagePost.getContent();
        this.currentPage = pagePost.getNumber();
        this.isFirst = pagePost.isFirst();
        this.hasNext = pagePost.hasNext();
        this.totalPages =  pagePost.getTotalPages();
        this.totalElements = pagePost.getTotalElements();
        this.posts = posts.stream().map(GetPostResponseDto::new).collect(Collectors.toList());
    }
}
