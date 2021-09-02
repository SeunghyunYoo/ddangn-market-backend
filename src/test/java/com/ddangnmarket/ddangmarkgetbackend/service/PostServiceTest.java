package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 초기 테스트를 위해 initDb에서 상품 5개를 등록해둔 상태
 *
 */
@SpringBootTest
@Transactional
@Rollback(value = false)
class PostServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    PostService postService;
    @Autowired
    EntityManager em;

    @Test
    void 게시글등록(){
    }

}















