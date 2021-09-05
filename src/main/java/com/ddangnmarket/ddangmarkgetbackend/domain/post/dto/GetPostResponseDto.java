package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.SaleStatus;
import lombok.Data;

@Data
public class GetPostResponseDto {

    private Long postId;

    private String title;

    private String desc;

    private int price;

    private SaleStatus status;

    private String sellerNickname;

    private CategoryTag categoryTag;

    private Dong dong;

    private String createdAt;

    private String updatedAt;

    public GetPostResponseDto(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
        this.desc = post.getDesc();
        this.price = post.getPrice();
        this.status = post.getSaleStatus();
        this.sellerNickname = post.getSeller().getNickname();
        this.categoryTag = post.getCategory().getCategoryTag();
        this.dong = post.getDistrict().getDong();
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
    }
}
