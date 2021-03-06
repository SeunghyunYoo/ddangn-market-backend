package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchase extends DeleteEntity{

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    private int score;

    @Lob
    private String review;


    public static Purchase createPurchase(Account buyer, Post post){
//        if (post.getsaleStatus().equals(saleStatus.COMPLETE)){
//            throw new IllegalStateException("구매 완료된 상품은 주문할 수 없습니다.");
//        }
        Purchase purchase = new Purchase();
        purchase.post = post;
        purchase.account = buyer;
        return purchase;
    }

    public void cancelPurchase(){
        post = null;
        account = null;
    }

    public void review(int score, String review){
        this.score = score;
        this.review = review;
    }
}
