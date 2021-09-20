package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.ChatStatus;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GetChatOneResponseDto {
    private Long chatId;
    private String chatRoomId;
    private ChatStatus chatStatus;
    private SellerDto seller;
    private BuyerDto buyer;
    private PostStatus postStatus;
    private String createdAt;
    private String updatedAt;

    public GetChatOneResponseDto(Chat chat){
        this.chatId = chat.getId();
        this.chatRoomId = chat.getChatRoom().getRoomId();
        this.chatStatus = chat.getChatStatus();
        this.seller = new SellerDto(chat.getPost().getSeller());
        this.buyer = new BuyerDto(chat.getAccount());
        this.postStatus = chat.getPost().getPostStatus();
        this.createdAt = chat.getCreatedAt().toString();
        this.updatedAt = chat.getUpdatedAt().toString();
    }

    @Data
    @NoArgsConstructor
    static class SellerDto{
        private String nickname;
        private double mannerTemp;
        private Dong dong;

        SellerDto(Account account){
            this.nickname = account.getNickname();
            this.mannerTemp = account.getMannerTemp();
            this.dong = account.getActivityArea().getDistrict().getDong();
        }
    }

    @Data
    @NoArgsConstructor
    static class BuyerDto{
        private String nickname;
        private double mannerTemp;
        private Dong dong;

        BuyerDto(Account account){
            this.nickname = account.getNickname();
            this.mannerTemp = account.getMannerTemp();
            this.dong = account.getActivityArea().getDistrict().getDong();
        }
    }

    @Data
    @NoArgsConstructor
    static class PostDto{
        private Long postId;
        private PostStatus postStatus;
        private String title;
        private Dong dong;
        private int price;

        PostDto(Post post){
            this.postId = post.getId();
            this.postStatus = post.getPostStatus();
            this.title = post.getTitle();
            this.dong = post.getDistrict().getDong();
            this.price = post.getPrice();
        }
    }
}
