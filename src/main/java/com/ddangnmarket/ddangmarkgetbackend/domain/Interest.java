package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interest {

    @Id @GeneratedValue
    @Column(name = "interest_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public Interest(Post post, Account account){
        this.post = post;
        this.account = account;
    }

    public static Interest enrollInterest(Post post, Account account){
//        post.setStatus(Status.BOOKED);
        return new Interest(post, account);
    }

    public void removeInterest(){
        this.account = null;
    }
}
