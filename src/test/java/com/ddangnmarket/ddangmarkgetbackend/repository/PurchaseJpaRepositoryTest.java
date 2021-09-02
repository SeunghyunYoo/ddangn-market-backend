package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PurchaseJpaRepositoryTest {

    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    PurchaseJpaRepository purchaseJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 구매테스트(){

    }

    @Test
    void 구매자별로조회(){

    }


}