package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Reserve;
import com.ddangnmarket.ddangmarkgetbackend.domain.Status;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.DIGITAL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class ReserveJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired PostJpaRepository postJpaRepository;
    @Autowired ReserveJpaRepository reserveJpaRepository;

    @Test
    void 예약등록(){
        Account seller = new Account("seller");
        Account account = new Account("account");
        em.persist(seller);
        em.persist(account);


        Post post1 = Post.post("맥북", seller, DIGITAL);
        Post post2 = Post.post("그램", seller, DIGITAL);
        Post post3 = Post.post("XPS", seller, DIGITAL);

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Reserve reserve = Reserve.reserve(post1, account);

        reserveJpaRepository.save(reserve);

        em.flush();
        em.clear();

        List<Reserve> reserves = reserveJpaRepository.findAll();
        assertThat(reserves.size()).isEqualTo(1);
    }

    @Test
    void 판매자로조회(){
        Account seller = new Account("seller");
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(seller);
        em.persist(account1);
        em.persist(account2);


        Post post1 = Post.post("맥북", seller, DIGITAL);
        Post post2 = Post.post("그램", seller, DIGITAL);
        Post post3 = Post.post("XPS", seller, DIGITAL);

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);

        Reserve reserve1 = Reserve.reserve(post1, account1);
        Reserve reserve2 = Reserve.reserve(post2, account2);

        reserveJpaRepository.save(reserve1);
        reserveJpaRepository.save(reserve2);

        em.flush();
        em.clear();

        List<Reserve> allBySellerId = reserveJpaRepository.findAllBySellerId(seller.getId());
        assertThat(allBySellerId.size()).isEqualTo(2);

        List<Post> allBySellerAndStatus = postJpaRepository.findAllBySellerAndStatus(seller, Status.RESERVE);
        assertThat(allBySellerAndStatus.size()).isEqualTo(2);
    }
}