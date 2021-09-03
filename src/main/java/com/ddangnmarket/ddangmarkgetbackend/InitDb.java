package com.ddangnmarket.ddangmarkgetbackend;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final CategoryJpaRepository categoryJpaRepository;

    @PostConstruct
    public void init(){
        initService.initCategory();
        initService.initAccountPost();
        initService.initAccount();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        private final CategoryJpaRepository categoryJpaRepository;

        public void initCategory(){
            for(CategoryTag categoryTag : CategoryTag.values()){
                 em.persist(new Category(categoryTag));
            }
        }

        public void initAccountPost(){
            Account account1 = new Account("account1", "000-0000-0000", "account1@gmail.com", "00000000");
            em.persist(account1);

            Account account2= new Account("account2", "000-0000-0000", "account2@gmail.com", "00000000");
            em.persist(account2);

            Account account3 = new Account("account3", "000-0000-0000", "account3@gmail.com", "00000000");
            em.persist(account3);

            Category digital = categoryJpaRepository.findByCategoryTag(CategoryTag.DIGITAL);
            Category book = categoryJpaRepository.findByCategoryTag(CategoryTag.BOOK);
            Category clothes = categoryJpaRepository.findByCategoryTag(CategoryTag.CLOTHES);
            Category furniture = categoryJpaRepository.findByCategoryTag(CategoryTag.FURNITURE);

            Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, digital, account1);
            em.persist(post1);
            Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, book, account1);
            em.persist(post2);
            Post post3 = Post.createPost("티셔츠 판매 ", "티셔츠 팔아요", 100000, clothes, account1);
            em.persist(post3);
            Post post4 = Post.createPost("책상 판매", "책상 팔아요", 10000, furniture, account1);
            em.persist(post4);

            Chat chat1 = Chat.createChat(post1, account2);
            em.persist(chat1);
            Chat chat2 = Chat.createChat(post1, account3);
            em.persist(chat2);

            Chat chat3 = Chat.createChat(post2, account1);
            em.persist(chat3);
        }

        public void initAccount(){
        }


    }
}
