package com.ddangnmarket.ddangmarkgetbackend.repository;

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

    public List<Interest> findAll(){
        return em.createQuery("select i from Interest i", Interest.class).getResultList();
    }

    public List<Interest> findAllByAccount(Account account){
        return em.createQuery("select i from Interest i" +
                " where i.account = :account", Interest.class)
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
