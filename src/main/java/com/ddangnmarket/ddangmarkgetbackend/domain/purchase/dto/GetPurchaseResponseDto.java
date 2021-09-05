package com.ddangnmarket.ddangmarkgetbackend.domain.purchase.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.SaleStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class GetPurchaseResponseDto {

    private Long purchaseId;
    private PostDto post;
    private String buyerNickname;
    private String createdAt;
    private String updatedAt;

    public GetPurchaseResponseDto(Purchase purchase){

        this.purchaseId = purchase.getId();
        this.post = new PostDto(purchase.getPost());
        this.buyerNickname = purchase.getAccount().getNickname();
        this.createdAt = purchase.getCreatedAt().toString();
        this.updatedAt = purchase.getUpdatedAt().toString();

    }

    @Data
    @NoArgsConstructor
    static class PostDto {
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

        PostDto(Post post){
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
}
