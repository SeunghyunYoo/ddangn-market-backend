package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetPostResponseDto {

    private Long postId;

    private String title;

//    private String desc;

    private int price;

    private int viewCount;

    private int chatCount;

    private int interestCount;

    private PostStatus status;

    private SellerDto seller;

    private CategoryTag categoryTag;

    private Dong dong;

    private String createdAt;

    private String updatedAt;

    public GetPostResponseDto(Post post){
        this.postId = post.getId();
        this.title = post.getTitle();
//        this.desc = post.getDesc();
        this.price = post.getPrice();
        this.viewCount = post.getViewCount();
        this.chatCount = post.getChatCount();
        this.interestCount = post.getInterestCount();
        this.status = post.getPostStatus();
        this.seller = new SellerDto(post.getSeller());
        this.categoryTag = post.getCategory().getCategoryTag();
        this.dong = post.getDistrict().getDong();
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();
    }

    @Data
    @NoArgsConstructor
    static class SellerDto {
        private String nickname;
        private double mannerTemp;

        SellerDto(Account account){
            this.nickname = account.getNickname();
            this.mannerTemp = account.getMannerTemp();
        }
    }
}
