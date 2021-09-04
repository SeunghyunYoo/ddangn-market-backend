package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private PostStatus postStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account seller;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "post")
    private List<Chat> chats = new ArrayList<>();


    // == 생성 메서드 == //
    public static Post createPost(String title, String desc, int price, Category category, Account seller){
        Post post = new Post();
        post.title = title;
        post.desc = desc;
        post.price = price;
        post.category = category;
        post.seller = seller;
        post.postStatus = PostStatus.NEW;
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

    public void changeReserve(Chat chat){
        Optional<Chat> optChat = chats.stream()
                .filter(c -> c.getChatStatus() == ChatStatus.RESERVED)
                .findAny();

        optChat.ifPresent(Chat::cancelReserve);
        chat.changeReserve();
        postStatus = PostStatus.RESERVE;
    }

    public void cancelReserve(){
        chats.forEach(Chat::cancelReserve);
        postStatus = PostStatus.NEW;
    }

}
