package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InterestJpaRepository {

    private final EntityManager em;

    public Interest save(Interest interest){
        em.persist(interest);
        return interest;
    }

    public void delete(Interest interest){
        em.remove(interest);
    };

    public Optional<Interest> findByIdAndAccount(Long id, Account account){
        return em.createQuery("select i from Interest i" +
                " where i.id = :id" +
                " and i.account = :account", Interest.class)
                .setParameter("id", id)
                .setParameter("account", account)
                .getResultStream()
                .findAny();
    }

    public List<Interest> findAllByAccount(Account account){
        return em.createQuery("select i from Interest i" +
                " join fetch i.post p" +
                " join fetch p.seller s" +
                " join fetch p.category c" +
                " join fetch i.account a" +
                " where a = :account", Interest.class)
                .setParameter("account", account)
                .getResultList();
    }

    public Optional<Interest> findByPostId(Long id){
        Optional<Interest> interestOpt = em.createQuery("select i from Interest i" +
                        " where i.post.id =: id", Interest.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();
        return interestOpt;
    }
}
