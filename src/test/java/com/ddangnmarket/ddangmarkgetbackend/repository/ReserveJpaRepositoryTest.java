package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.reserve.ReserveJpaRepository;
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
class ReserveJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    ReserveJpaRepository reserveJpaRepository;

    @Test
    void 예약등록(){
    }

    @Test
    void 판매자로조회(){

    }
}