package com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetAllPostResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPostResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetAllChatResponseDto {
    private List<GetChatResponseDto> chats;

    public GetAllChatResponseDto(List<Chat> chats){
        this.chats = chats.stream().map(GetChatResponseDto::new).collect(Collectors.toList());
    }

    @Data
    @NoArgsConstructor
    public static class GetChatResponseDto {
        private Long chatId;
        private SellerDto seller;
        private BuyerDto buyer;
        private PostDto post;
        private String createdAt;
        private String updatedAt;

        public GetChatResponseDto(Chat chat){
            this.chatId = chat.getId();
            this.seller = new SellerDto(chat.getPost().getSeller());
            this.buyer = new BuyerDto(chat.getAccount());
            this.post = new PostDto(chat.getPost());
            this.createdAt = chat.getCreatedAt().toString();
            this.updatedAt = chat.getUpdatedAt().toString();
        }

        @Data
        @NoArgsConstructor
        public static class SellerDto{
            private String nickname;
            private Dong dong;

            SellerDto(Account account){
                this.nickname = account.getNickname();
                this.dong = account.getActivityArea().getDistrict().getDong();
            }
        }

        @Data
        @NoArgsConstructor
        public static class BuyerDto{
            private String nickname;
            private Dong dong;

            BuyerDto(Account account){
                this.nickname = account.getNickname();
                this.dong = account.getActivityArea().getDistrict().getDong();
            }
        }

        @Data
        @NoArgsConstructor
        public static class PostDto{
            private Long postId;
            private PostStatus postStatus;
            private Dong dong;

            PostDto(Post post){
                this.postId = post.getId();
                this.postStatus = post.getPostStatus();
                this.dong = post.getDistrict().getDong();
            }
        }
    }
}
