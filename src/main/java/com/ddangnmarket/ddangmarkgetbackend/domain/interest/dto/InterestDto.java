package com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InterestDto {
    private Long id;
    private PostDto post;
    private String createdAt;
    private String updatedAt;

    public InterestDto(Interest interest){
        this.id = interest.getId();
        this.post = new PostDto(interest.getPost());
        this.createdAt = interest.getCreatedAt().toString();
        this.updatedAt = interest.getUpdatedAt().toString();

    }

    @Data
    @NoArgsConstructor
    public static class PostDto{
        private Long id;
        private String title;
        private int price;
        private CategoryTag category;
        private String createdAt;
        private String updatedAt;
        private SellerDto seller;
        private Dong dong;

        PostDto(Post post){
            this.id = post.getId();
            this.title = post.getTitle();
            this.price = post.getPrice();
            this.category = post.getCategory().getCategoryTag();
            this.seller = new SellerDto(post.getSeller());
            this.dong = post.getDistrict().getDong();
            this.createdAt = post.getCreatedAt().toString();
            this.updatedAt = post.getUpdatedAt().toString();
        }
    }

    @Data
    @NoArgsConstructor
    public static class SellerDto{
        private String nickname;
        private String phone;

        SellerDto(Account account){
            this.nickname = account.getNickname();
            this.phone = account.getPhone();
        }
    }
}
