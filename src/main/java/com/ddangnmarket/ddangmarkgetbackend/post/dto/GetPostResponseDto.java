package com.ddangnmarket.ddangmarkgetbackend.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Status;
import lombok.Data;

import javax.persistence.*;

@Data
public class GetPostResponseDto {

    private Long postId;

    private String title;

    private String desc;

    private int price;

    private Status status;

    private String sellerNickname;

    private CategoryTag categoryTag;

    public GetPostResponseDto(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.desc = post.getDesc();
        this.price = post.getPrice();
        this.status = post.getStatus();
        this.sellerNickname = post.getSeller().getNickname();
        this.categoryTag = post.getCategoryTag();
    }
}
