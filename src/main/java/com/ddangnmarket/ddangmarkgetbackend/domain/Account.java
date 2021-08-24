package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Account {
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String nickname;

    private String phone;

    private String mail;

    private String password;

    public Account(String nickname){
        this.nickname = nickname;
    }
    public Account(String nickname, String phone, String mail){
        this.nickname = nickname;
        this.phone = phone;
        this.mail = mail;
    }

    public Account(String nickname, String phone, String mail, String password){
        this.nickname = nickname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
    }

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();

    //== 연관관계 메서드 ==/
    public void addPost(Post post){
        posts.add(post);
    }
}
