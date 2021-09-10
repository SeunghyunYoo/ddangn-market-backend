package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = false)
public class Account extends DeleteEntity{
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
        this.mannerTemp = 37.5;
    }

    @OneToMany(mappedBy = "seller", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Post> posts = new ArrayList<>();

    private double mannerTemp;

    //== 연관관계 메서드 ==/
    public void addPost(Post post){
        posts.add(post);
    }

    //== 비즈니스 메서드 ==/
    public void absoluteDeleteAccount(){
        posts.forEach(Post::absoluteDeletePost);
    }

    public void deleteAccount(){
        this.nickname = UUID.randomUUID().toString();
        this.password = UUID.randomUUID().toString();
        posts.forEach(Post::deletePost);
        super.delete();
    }

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
