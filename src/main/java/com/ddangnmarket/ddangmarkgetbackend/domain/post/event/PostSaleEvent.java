package com.ddangnmarket.ddangmarkgetbackend.domain.post.event;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.context.ApplicationEvent;

/**
 * @author SeunghyunYoo
 */
@Getter
public class PostSaleEvent {
    private Post post;
    private Account buyer;

    public PostSaleEvent(Post post, Account buyer){
        this.post = post;
        this.buyer = buyer;
    }

}
