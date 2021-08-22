package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reserve {

    @Id @GeneratedValue
    @Column(name = "reserve_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Reserve(Post post, Account account){
        this.post = post;
        this.account = account;
    }

    public static Reserve reserve(Post post, Account account){
        post.setStatus(Status.RESERVE);
        return new Reserve(post, account);
    }

    public void cancel(){
        this.account = null;
        this.post.setStatus(Status.NEW);
    }
}
