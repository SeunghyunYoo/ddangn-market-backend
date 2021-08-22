package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PurchaseJpaRepositoryTest {

    @Autowired AccountJpaRepository accountJpaRepository;
    @Autowired PostJpaRepository postJpaRepository;
    @Autowired PurchaseJpaRepository purchaseJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 구매테스트(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);


        Post post1 = new Post("title", account1);
        Post post2 = new Post("title2", account1);
        Post post3 = new Post("title3", account2);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);

        em.flush();
        em.clear();


        Purchase purchase = Purchase.purchase(post1, account2);

        purchaseJpaRepository.save(purchase);

        em.flush();
        em.clear();

        List<Purchase> purchases = purchaseJpaRepository.findAll();
        assertThat(purchases.size()).isEqualTo(1);
    }

    @Test
    void 구매자별로조회(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);

        Post post1 = new Post("title", account1);
        Post post2 = new Post("title2", account1);
        Post post3 = new Post("title3", account2);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);


        Purchase purchase = Purchase.purchase(post1, account2);
        Purchase purchase2 = Purchase.purchase(post2, account2);

        purchaseJpaRepository.save(purchase);
        purchaseJpaRepository.save(purchase2);

        em.flush();
        em.clear();

        List<Purchase> purchases = purchaseJpaRepository.findAllByAccount(account2);
        assertThat(purchases.size()).isEqualTo(2);
    }


}