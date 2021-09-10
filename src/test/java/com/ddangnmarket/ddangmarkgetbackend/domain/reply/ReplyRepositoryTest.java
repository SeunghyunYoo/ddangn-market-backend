package com.ddangnmarket.ddangmarkgetbackend.domain.reply;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReplyRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired ReplyRepository replyRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Test
    @Rollback(value = false)
    void ReplyTest(){
        Account account01 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        District gumi = districtRepository.findByDong(Dong.GUMI);
        account01.setActivityArea(ActivityArea.createActiveArea(gumi, 0));
        accountRepository.save(account01);

        Account account02 = new Account("account02", "000-0000-0000", "account02@gmail.com", "00000000");
        account02.setActivityArea(ActivityArea.createActiveArea(gumi, 0));
        accountRepository.save(account02);

        em.flush();
        em.clear();

        Account account1 = accountRepository.findById(account01.getId()).orElseThrow();
        Account account2 = accountRepository.findById(account01.getId()).orElseThrow();
        Category digital = categoryJpaRepository.findByCategoryTag(CategoryTag.DIGITAL);
        Post post01 = postRepository.save(Post.createPost("post01", "post01", 1000, digital, account1));

        em.flush();
        em.clear();
        Account account11 = accountRepository.findById(account01.getId()).orElseThrow();
        Account account22 = accountRepository.findById(account01.getId()).orElseThrow();
        Post post = postRepository.findById(post01.getId()).orElseThrow();
        Reply reply = Reply.createReply(post, account11, "싸게 팔아요");
        Reply.createReply(post, account22, "가격 제시되나요");

        em.flush();
        em.clear();
        System.out.println("=====================");
        Post post1 = postRepository.findById(post.getId()).orElseThrow();
        List<Reply> replies = post1.getReplies();

        replyRepository.deleteById(reply.getId());
        postRepository.deleteById(post.getId());
    }
}