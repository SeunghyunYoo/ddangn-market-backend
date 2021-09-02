package com.ddangnmarket.ddangmarkgetbackend.domain.reserve;

import com.ddangnmarket.ddangmarkgetbackend.domain.Reserve;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ReserveJpaRepository {

    private final EntityManager em;

    public Reserve save(Reserve reserve){
        em.persist(reserve);
        return reserve;
    }

    public void delete(Reserve reserve){
        em.remove(reserve);
    }

    public Reserve findByPostId(Long id){
        Optional<Reserve> reserveOpt = em.createQuery("select r from Reserve r" +
                        " where r.post.id = :id", Reserve.class)
                .setParameter("id", id)
                .getResultList().stream().findAny();

        return reserveOpt.orElse(null);
    }

    public List<Reserve> findAllByAccountId(Long id){
        return em.createQuery("select r from Reserve r" +
                " where r.account.id = :id", Reserve.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Reserve> findAllBySellerId(Long id){
        return em.createQuery("select r from Reserve r" +
                " where r.post.seller.id = : id", Reserve.class)
                .setParameter("id", id)
                .getResultList();
    }

    public List<Reserve> findAll() {
        return em.createQuery("select r from Reserve r", Reserve.class).getResultList();
    }
}
