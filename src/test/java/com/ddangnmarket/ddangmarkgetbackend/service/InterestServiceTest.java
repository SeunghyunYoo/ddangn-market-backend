package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.InterestJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.InterestService;
import org.junit.jupiter.api.Assertions;
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
class InterestServiceTest {

    @Autowired
    InterestService interestService;
    @Autowired
    InterestJpaRepository interestJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 관심상품등록(){

    }

    @Test
    void 관심상품삭제(){

    }

    @Test
    @Rollback()
    void 존재하지않는게시글회원(){

    }


}