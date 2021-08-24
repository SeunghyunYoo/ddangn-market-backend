package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id @GeneratedValue
    @Column(name = "post_id")
    private Long id;

    private String title;

    @Lob
    private String desc;

    private int price;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account seller;

    @Enumerated(EnumType.STRING)
    private CategoryTag categoryTag;
    /*@OneToOne
    @JoinColumn(name = "category_id")
    private Category category;*/

    public Post(String title, Account seller){
        this.title = title;
        this.seller = seller;
        status = Status.NEW;
    }

    //== 연관관계 메서드 ==/
    public void setSeller(Account seller) {
        this.seller = seller;
        seller.addPost(this);
    }

    //== 바즈니스 로직 ==//
    public static Post post(String title, Account seller, CategoryTag categoryTag){
        Post post = new Post(title, seller);
        post.setCategoryTag(categoryTag);
        return post;
    }

}
