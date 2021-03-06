package com.ddangnmarket.ddangmarkgetbackend.domain.purchase;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PreUpdate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PurchaseJpaRepository {

    private final EntityManager em;

    public Purchase save(Purchase purchase){
        em.persist(purchase);
        return purchase;
    }

    public void delete(Purchase purchase){
        em.remove(purchase);
    }


    public Optional<Purchase> findById(Long id){
        return em.createQuery("select p from Purchase p" +
                        " where p.id =: id", Purchase.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();
    }

    public List<Purchase> findAll(){
        return em.createQuery("select p from Purchase p", Purchase.class)
                .getResultList();
    }

    public List<Purchase> findAllByAccount(Account account){
        return em.createQuery("select pc from Purchase pc" +
                " join fetch pc.post p" +
                " join fetch p.category c" +
                " join fetch p.district d" +
                " join fetch p.seller s" +
                " where pc.account = : account", Purchase.class)
                .setParameter("account", account)
                .getResultList();
    }

}
