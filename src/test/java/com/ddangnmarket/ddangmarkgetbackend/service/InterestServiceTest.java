package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.repository.InterestJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class InterestServiceTest {

    @Autowired
    InterestService interestService;
    @Autowired
    InterestJpaRepository interestJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 관심상품등록(){
        Account seller1 = new Account("Seller1");
        Account seller2 = new Account("Seller2");
        Account account = new Account("account1");
        em.persist(seller1);
        em.persist(seller2);
        em.persist(account);

        Post post1 = Post.post("맥북판매", seller1, CategoryTag.DIGITAL);
        Post post2 = Post.post("그램판매", seller1, CategoryTag.DIGITAL);
        Post post3 = Post.post("m1 맥북 판매", seller2, CategoryTag.DIGITAL);
        Post post4 = Post.post("이펙티브자바 판매", seller2, CategoryTag.BOOK);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);

        Interest interest = interestService.enrollInterest(post1.getId(), account.getId());
        em.flush();
        em.clear();

        Interest findInterest = em.find(Interest.class, interest.getId());
    }

    @Test
    void 관심상품삭제(){
        Account seller1 = new Account("Seller1");
        Account seller2 = new Account("Seller2");
        Account account = new Account("account1");
        em.persist(seller1);
        em.persist(seller2);
        em.persist(account);

        Post post1 = Post.post("맥북판매", seller1, CategoryTag.DIGITAL);
        Post post2 = Post.post("그램판매", seller1, CategoryTag.DIGITAL);
        Post post3 = Post.post("m1 맥북 판매", seller2, CategoryTag.DIGITAL);
        Post post4 = Post.post("이펙티브자바 판매", seller2, CategoryTag.BOOK);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);

        Interest interest = Interest.enrollInterest(post1, account);
        em.persist(interest);
        em.flush();
        em.clear();

        interestService.removeInterest(post1.getId());
        List<Interest> all = interestJpaRepository.findAll();
        assertThat(all.size()).isEqualTo(0);
    }

    @Test
    @Rollback()
    void 존재하지않는게시글회원(){
        Account seller1 = new Account("Seller1");
        Account account = new Account("account1");
        em.persist(seller1);
        em.persist(account);

        Post post1 = Post.post("맥북판매", seller1, CategoryTag.DIGITAL);
        em.persist(post1);

        em.flush();
        em.clear();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> interestService.enrollInterest(100L, account.getId()),
                "존재하지 않는 게시글 입니다.");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> interestService.enrollInterest(post1.getId(), 100L),
                "존재하지 않는 회원 입니다.");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> interestService.removeInterest(post1.getId()),
                "존재하지 않는 게시글 입니다.");
    }


}