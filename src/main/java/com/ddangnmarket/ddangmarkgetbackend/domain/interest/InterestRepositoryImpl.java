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
public class InterestRepositoryImpl implements InterestRepositoryCustom{

    private final EntityManager em;

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

    public Optional<Interest> findByPostId(Long postId){
        Optional<Interest> interestOpt = em.createQuery("select i from Interest i" +
                        " where i.post.id =: postId", Interest.class)
                .setParameter("postId", postId)
                .getResultList().stream().findAny();
        return interestOpt;
    }
}
