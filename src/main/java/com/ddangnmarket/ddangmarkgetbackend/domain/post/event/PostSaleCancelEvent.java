package com.ddangnmarket.ddangmarkgetbackend.domain.post.event;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import lombok.Getter;

/**
 * @author SeunghyunYoo
 */
@Getter
public class PostSaleCancelEvent {
    private Post post;
    private Account buyer;

    public PostSaleCancelEvent(Post post, Account buyer){
        this.post = post;
        this.buyer = buyer;
    }
}
