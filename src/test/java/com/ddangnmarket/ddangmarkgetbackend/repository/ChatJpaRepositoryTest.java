package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ChatJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    ChatJpaRepository chatJpaRepository;

    @Test
    @Rollback(value = false)
    void 예약등록(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);
        Account account2 = new Account("account02", "000-0000-0000", "account02@gmail.com", "00000000");
        em.persist(account2);

//        List<Post> posts1 = postJpaRepository.findAllByCategory(CategoryTag.DIGITAL);
//        Post digital = posts1.get(0);
//
//        List<Post> post2 = postJpaRepository.findAllByCategory(CategoryTag.CLOTHES);
//        Post clothes = post2.get(0);
//
//        Chat chat1A = Chat.createChat(digital, account1);
//        Chat chat1B = Chat.createChat(clothes, account1);
//        chatJpaRepository.save(chat1A);
//        chatJpaRepository.save(chat1B);
//
//        Chat chat2A = Chat.createChat(digital, account2);
//        chatJpaRepository.save(chat2A);
//
//        em.flush();
//        em.clear();
//
//        Chat findChat = chatJpaRepository.findByAccountAndPost(account1.getId(), digital.getId())
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약 상품입니다."));;
//        assertThat(findChat.getPost().getId()).isEqualTo(digital.getId());
//
//        ///
//        List<Chat> allByAccount1 = chatJpaRepository.findAllByAccount(account1.getId());
//        assertThat(allByAccount1.size()).isEqualTo(2);
//
//        List<Chat> allByAccount2 = chatJpaRepository.findAllByAccount(account2.getId());
//        assertThat(allByAccount2.size()).isEqualTo(1);
//
//        ////
//        List<Chat> allByDigital = chatJpaRepository.findAllByPost(digital.getId());
//        assertThat(allByDigital.size()).isEqualTo(2);
//
//        System.out.println("allByDigital = " + allByDigital.get(0).getAccount().getNickname());


    }

    @Test
    void 판매자로조회(){

    }
}