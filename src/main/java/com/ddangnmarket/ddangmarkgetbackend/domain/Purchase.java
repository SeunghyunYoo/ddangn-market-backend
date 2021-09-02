package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase {

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Purchase (Post post, Account account){
        this.post = post;
        this.account = account;
    }

    public static Purchase purchase(Post post, Account account){
        post.setPostStatus(PostStatus.COMPLETE);
        return new Purchase(post, account);
    }
}
