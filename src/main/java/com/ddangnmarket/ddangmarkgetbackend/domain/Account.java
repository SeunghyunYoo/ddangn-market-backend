package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "activity_area_id")
    private ActivityArea activityArea;
    // TODO 1. Controller test
    // TODO 2. post에도 저장

    public Account(String nickname, String phone, String mail, String password){
        this.nickname = nickname;
        this.phone = phone;
        this.mail = mail;
        this.password = password;
    }

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Post> posts = new ArrayList<>();

    //== 연관관계 메서드 ==/
    public void addPost(Post post){
        posts.add(post);
    }

    //== 비즈니스 메서드 ==/
    public void changePassword(String password){
        this.password = password;
    }

    public void changeAccountInfo(String nickname, String phone){
        this.nickname = nickname;
        this.phone = phone;
    }

    public void changeActivityArea(ActivityArea activityArea){
        this.activityArea = activityArea;
        activityArea.updateAccount(this);
        // 유저 활동 지역 바꿔도 기존에 등록한 게시글의 위치는 유지하도록 설정
        // posts.forEach(post ->
        //         post.changeDistrict(activityArea.getDistrict()));
    }
}
