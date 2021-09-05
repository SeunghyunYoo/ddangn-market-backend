package com.ddangnmarket.ddangmarkgetbackend.domain.sale;

import com.ddangnmarket.ddangmarkgetbackend.domain.Sale;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class SaleJpaRepository {

    private final EntityManager em;

    public Long save(Sale sale){
        em.persist(sale);
        return sale.getId();
    }

    public void delete(Sale sale){
        em.remove(sale);
    }
}
