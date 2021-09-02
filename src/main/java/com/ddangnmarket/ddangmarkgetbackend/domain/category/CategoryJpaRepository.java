package com.ddangnmarket.ddangmarkgetbackend.domain.category;

import com.ddangnmarket.ddangmarkgetbackend.domain.Category;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class CategoryJpaRepository {

    private final EntityManager em;

    public Category save(Category category){
        em.persist(category);
        return category;
    }

    public Category findByCategoryTag(CategoryTag categoryTag){
        return em.createQuery("select c from Category c" +
                        " where c.categoryTag = :categoryTag", Category.class)
                .setParameter("categoryTag", categoryTag)
                .getResultStream()
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 품목입니다."));
    }
}
