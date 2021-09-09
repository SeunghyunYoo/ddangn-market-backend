package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class Interest extends DeleteEntity{

    @Id @GeneratedValue
    @Column(name = "interest_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Interest addInterest(Post post, Account account){
        Interest interest = new Interest();
        interest.post = post;
        interest.account = account;
        post.addInterestCount();
        return interest;
    }

}
