package com.ddangnmarket.ddangmarkgetbackend;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Category;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

//    @PostConstruct
    public void init(){
        initService.initSeller("판매자1", "A");
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;

        public void initCategory(){

            initSeller("판매자1", "A");
        }

        public void initSeller(String nickname, String title){
            Account account = new Account(nickname);
            em.persist(account);

            Post post1 = Post.post("맥북" + title, account, DIGITAL);
            Post post2 = Post.post("그램" + title, account, DIGITAL);
            Post post3 = Post.post("양말" + title, account, CLOTHES);
            Post post4 = Post.post("티셔츠" + title, account, CLOTHES);
            Post post5 = Post.post("책상" + title, account, APPLIANCE);

            em.persist(post1);
            em.persist(post2);
            em.persist(post3);
            em.persist(post4);
            em.persist(post5);
        }
    }
}
