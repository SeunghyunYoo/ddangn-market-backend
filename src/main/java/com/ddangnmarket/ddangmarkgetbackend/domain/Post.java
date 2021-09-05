package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.SaleStatus;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"title", "desc", "price"}, callSuper = false)
// 같은 내용의 게시글 등록 허용
public class Post extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String desc;

    private int price;

    @Enumerated(EnumType.STRING)
    private SaleStatus saleStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<Chat> chats = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    private District district;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private Sale sale;

    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private Purchase purchase;

    // == 생성 메서드 == //
    public static Post createPost(String title, String desc, int price, Category category, Account seller){
        Post post = new Post();
        post.title = title;
        post.desc = desc;
        post.price = price;
        post.category = category;
        post.seller = seller;
        post.saleStatus = SaleStatus.NEW;
        post.district = seller.getActivityArea().getDistrict();
        seller.addPost(post);
        return post;
    }

    //== 연관관계 메서드 ==/
    public void setSeller(Account seller) {
        this.seller = seller;
        seller.addPost(this);
    }

    //== 바즈니스 로직 ==//

    public void addChat(Chat chat){
        chats.add(chat);
    }

    public void updatePost(String title, String desc, int price, Category category){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.category = category;
    }

    public void changeComplete(){
        saleStatus = SaleStatus.COMPLETE;
    }

    public void cancelSale(){
        sale.cancelSale();
        purchase.cancelPurchase();
        sale = null;
        purchase = null;
        saleStatus = SaleStatus.NEW;
        // mappedBy라 여기서 변경 못함 Chat에서
        chats.forEach(Chat::changeNone);
    }

    public void changeReserve(Chat chat){
        // 다른 chat으로 예약 변경 -> 기존의 reserved 였던 것들 취소
        // -> target chat 예약으로 지정
//        chats.stream()
//               .filter(c -> c.getChatStatus() == ChatStatus.RESERVED)
//               .findAny()
//               .ifPresent(Chat::changeNone);
        chats.forEach(Chat::changeNone);

        chat.changeReserve();
        if (sale != null){
            sale.cancelSale();
            sale = null;
        }
        if (purchase != null){
            purchase.cancelPurchase();
            purchase = null;
        }
        saleStatus = SaleStatus.RESERVE;
    }

    public void cancelReserve(){
        chats.forEach(Chat::changeNone);
        if (sale != null){
            sale.cancelSale();
            sale = null;
        }
        if (purchase != null){
            purchase.cancelPurchase();
            purchase = null;
        }
        saleStatus = SaleStatus.NEW;
    }

    public void changeDistrict(District district){
        this.district = district;
    }

}
