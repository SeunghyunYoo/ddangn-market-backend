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
public class Purchase extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @Enumerated(EnumType.STRING)
    private PurchaseStatus purchaseStatus;

    public static Purchase purchase(Post post, Account account){
        if (post.getPostStatus().equals(PostStatus.COMPLETE)){
            throw new IllegalStateException("구매 완료된 상품은 주문할 수 없습니다.");
        }
        Purchase purchase = new Purchase();
        purchase.post = post;
        purchase.account = account;
        purchase.purchaseStatus = PurchaseStatus.COMPLETE;
        post.setPostStatus(PostStatus.COMPLETE);
        return purchase;
    }
}
