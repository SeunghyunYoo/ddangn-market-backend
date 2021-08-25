package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.post.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class PostRepositoryTest {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    EntityManager em;

    @Test
    void 등록테스트(){
        Account seller = new Account("seller");

        Account saveAccount = accountRepository.save(seller);

//        em.flush();
//        em.clear();

        Post post = new Post("맥북판매", seller);

        Post savePost = postRepository.save(post);

        em.flush();
        em.clear();

        Post findPost = postRepository.findById(savePost.getId()).get();


        System.out.println(findPost.getSeller().getNickname());
    }

    @Test
    void 판매자아이디로조회(){

        Account seller = new Account("seller");

        Account saveAccount = accountRepository.save(seller);

        Post post = new Post("맥북판매", seller);

        Post savePost = postRepository.save(post);

        em.flush();
        em.clear();

        Account account = accountRepository.findById(seller.getId()).get();

        Post findPostByAccountId = postRepository.findBySellerNickname(account.getNickname());

        Assertions.assertThat(findPostByAccountId.getTitle()).isEqualTo("맥북판매");
        Assertions.assertThat(findPostByAccountId.getSeller().getNickname()).isEqualTo("seller");

        System.out.println("post = " + findPostByAccountId);
    }

}