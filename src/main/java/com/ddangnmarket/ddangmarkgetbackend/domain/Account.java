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
public class Account extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    private String nickname;

    private String phone;

    private String mail;

    private String password;

    public Account(String nickname, String phone, String mail, String password){
        this.nickname = nickname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
    }

    @OneToMany(mappedBy = "seller")
    private List<Post> posts = new ArrayList<>();

    //== 연관관계 메서드 ==/
    public void addPost(Post post){
        posts.add(post);
    }

    //== 비즈니스 메서드 ==/
    public void changePassword(String password){
        this.password = password;
    }

    public void changePhone(String phone){
        this.phone = phone;
    }

    public void changeNickname(String nickname){
        this.nickname = nickname;
    }

    public void changeAccountInfo(String nickname, String phone){
        this.nickname = nickname;
        this.phone = phone;
    }

}
