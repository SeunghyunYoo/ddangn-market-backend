package com.ddangnmarket.ddangmarkgetbackend;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Category;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.repository.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Arrays;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;
    private final CategoryJpaRepository categoryJpaRepository;

    @PostConstruct
    public void init(){
        initService.initCategory();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitService{
        private final EntityManager em;
        private final CategoryJpaRepository categoryJpaRepository;

        public void initCategory(){
            for(CategoryTag categoryTag : CategoryTag.values()){
                 em.persist(new Category(categoryTag));
            }
        }


    }
}
