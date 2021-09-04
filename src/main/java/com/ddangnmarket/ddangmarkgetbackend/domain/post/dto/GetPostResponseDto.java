package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.PostStatus;
import lombok.Data;

@Data
public class GetPostResponseDto {

    private Long postId;

    private String title;

    private String desc;

    private int price;

    private PostStatus status;

    private String sellerNickname;

    private CategoryTag categoryTag;

    private Dong dong;

    public GetPostResponseDto(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.desc = post.getDesc();
        this.price = post.getPrice();
        this.status = post.getPostStatus();
        this.sellerNickname = post.getSeller().getNickname();
        this.categoryTag = post.getCategory().getCategoryTag();
        this.dong = post.getDistrict().getDong();
    }
}
