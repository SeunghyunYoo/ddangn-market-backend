package com.ddangnmarket.ddangmarkgetbackend.repository;

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
                .getSingleResult();
    }
}
