package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = {"title", "desc", "price", "categoryTag"}) // 같은 내용의 게시글 등록 허용
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

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

    public Post(String title, String desc, int price, CategoryTag categoryTag, Account seller){
        this.title = title;
        this.desc = desc;
        this.price = price;
//        @Converter
        this.categoryTag = categoryTag;
        this.seller = seller;
        status = Status.NEW;
        seller.addPost(this);
    }

    public Post(String title, String desc, int price, Account seller){
        this.title = title;
        this.desc = desc;
        this.price = price;
//        @Converter
        this.postCategory = postCategory;
        this.seller = seller;
        status = Status.NEW;
        seller.addPost(this);
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

    public void setPostCategory(PostCategory postCategory){
        this.postCategory = postCategory;
        postCategory.setPost(this);
    }

}
