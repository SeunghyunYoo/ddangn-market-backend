package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;

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
    private PostStatus postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account seller;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "post_category_id")
    private PostCategory postCategory;

//    @Enumerated(EnumType.STRING)
//    private CategoryTag categoryTag;

//    public Post(String title, String desc, int price, CategoryTag categoryTag, Account seller){
//        this.title = title;
//        this.desc = desc;
//        this.price = price;
//        this.categoryTag = categoryTag;
//        this.seller = seller;
//        status = Status.NEW;
//        seller.addPost(this);
//    }

    private Post(String title, String desc, int price, PostCategory postCategory, Account seller){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.postCategory = postCategory;
        this.seller = seller;
        postStatus = PostStatus.NEW;
        // 연관관계
        seller.addPost(this);
        postCategory.setPost(this);
    }

    // == 생성 메서드 == //
    public static Post createPost(String title, String desc, int price, PostCategory postCategory, Account seller){
        return new Post(title, desc, price, postCategory, seller);
    }


    //== 연관관계 메서드 ==/
    public void setSeller(Account seller) {
        this.seller = seller;
        seller.addPost(this);
    }

    //== 바즈니스 로직 ==//
    public void setPostCategory(PostCategory postCategory){
        this.postCategory = postCategory;
        postCategory.setPost(this);
    }

}
