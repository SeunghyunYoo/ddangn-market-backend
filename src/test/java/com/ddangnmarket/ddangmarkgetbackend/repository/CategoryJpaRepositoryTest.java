package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.Category;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.DIGITAL;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class CategoryJpaRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;

    @Test
    void 카테고리태그로조회(){
        Category digitTag = categoryJpaRepository.findByCategoryTag(DIGITAL);
        assertThat(digitTag.getCategoryTag()).isEqualTo(DIGITAL);

    }

}