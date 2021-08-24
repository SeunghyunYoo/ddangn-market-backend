package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class PurchaseJpaRepository {

    private final EntityManager em;

    public Purchase save(Purchase purchase){
        em.persist(purchase);
        return purchase;
    }

    public List<Purchase> findAll(){
        return em.createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
    }

    public List<Purchase> findAllByAccount(Account account){
        return em.createQuery("select p from Purchase p" +
                " where p.account = : account", Purchase.class)
                .setParameter("account", account)
                .getResultList();
    }

}
