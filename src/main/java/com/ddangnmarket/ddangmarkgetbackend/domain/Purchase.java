package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Purchase {

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    public Purchase (Post post, Account account){
        this.post = post;
        this.account = account;
    }

    public static Purchase purchase(Post post, Account account){
        post.setStatus(Status.COMPLETE);
        return new Purchase(post, account);
    }
}
