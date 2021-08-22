package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 초기 테스트를 위해 initDb에서 상품 5개를 등록해둔 상태
 *
 */
@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired AccountService accountService;
    @Autowired PostService postService;
    @Autowired
    EntityManager em;

    @Test
    void 게시글등록(){
        Account seller1 = new Account("Seller1");
        Account seller2 = new Account("Seller2");
        em.persist(seller1);
        em.persist(seller2);

        Post post1 = Post.post("맥북판매", seller1, CategoryTag.DIGITAL);
        Post post2 = Post.post("그램판매", seller1, CategoryTag.DIGITAL);
        Post post3 = Post.post("m1 맥북 판매", seller2, CategoryTag.DIGITAL);
        Post post4 = Post.post("이펙티브자바 판매", seller2, CategoryTag.BOOK);
        postService.post(post1);
        postService.post(post2);
        postService.post(post3);
        postService.post(post4);

        List<Post> allPost = postService.findAll();

        assertThat(allPost.size()).isEqualTo(4);

        List<Post> allByCategoryTag = postService.findAllByCategoryTag(CategoryTag.DIGITAL);
        assertThat(allByCategoryTag.size()).isEqualTo(3);

    }

}















