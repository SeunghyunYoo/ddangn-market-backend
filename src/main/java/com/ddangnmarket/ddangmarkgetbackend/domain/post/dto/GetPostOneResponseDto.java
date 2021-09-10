package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Reply;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@Data
@NoArgsConstructor
public class GetPostOneResponseDto {

    private Long postId;

    private String title;

    private String desc;

    private int price;

    private int viewCount;

    private int chatCount;

    private int interestCount;

    private PostStatus status;

    private String sellerNickname;

    private double sellerMannerTemp;

    private CategoryTag categoryTag;

    private Dong dong;

    private List<ReplyDto> replies;

    private String createdAt;

    private String updatedAt;

    public GetPostOneResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.desc = post.getDesc();
        this.price = post.getPrice();
        this.viewCount = post.getViewCount();
        this.chatCount = post.getChatCount();
        this.interestCount = post.getInterestCount();
        this.status = post.getPostStatus();
        this.sellerNickname = post.getSeller().getNickname();
        this.sellerMannerTemp = post.getSeller().getMannerTemp();
        this.categoryTag = post.getCategory().getCategoryTag();
        this.dong = post.getDistrict().getDong();
        this.replies = post.getReplies().stream().map(ReplyDto::new).collect(toList());
        this.createdAt = post.getCreatedAt().toString();
        this.updatedAt = post.getUpdatedAt().toString();

    }
    @Data
    @NoArgsConstructor
    static class ReplyDto {
        private Long replyId;
        private String nickname;
        private String content;
        private String createdAt;
        private String updatedAt;

        ReplyDto(Reply reply) {
            this.replyId = reply.getId();
            this.nickname = reply.getAccount().getNickname();
            this.content = reply.getContent();
            this.createdAt = reply.getCreatedAt().toString();
            this.updatedAt = reply.getUpdatedAt().toString();
        }

    }
}
