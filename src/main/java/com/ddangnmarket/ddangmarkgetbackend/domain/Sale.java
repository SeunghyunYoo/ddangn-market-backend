package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale extends DeleteEntity {
    @Id @GeneratedValue
    @Column(name = "sale_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "post_id")
    private Post post;

    // TODO seller -> 판매 승인 -> sale table 저장
    //

    /**
     * Seller 판매 승인 -> sale 저장
     * -> post 상태 complete로 변경
     * -> chat 상태 complete로 변경
     * @param seller
     * @param post
     * @return
     */
    public static Sale createSale(Account seller, Post post){
        post.changeComplete();
        Sale sale = new Sale();
        sale.account = seller;
        sale.post = post;
        return sale;
    }

    /**
     * 취소시, 상품을 예약중, 판매중 둘중 1개로 돌려야함
     * reserved -> post reserved로 변경
     * chat -> reserved로 변경
     */
    public void cancelSale(){
        post = null;
        account = null;
    }

}
