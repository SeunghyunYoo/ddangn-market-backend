package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "reply_id")
    private Long id;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Reply createReply(Post post, Account account, String content){
        Reply reply = new Reply();
        reply.post = post;
        reply.account = account;
        reply.content = content;
        post.addReply(reply);
        return reply;
    }

    public void deleteReply(){
        post.removeReply(this);
        this.post = null;
    }

}
