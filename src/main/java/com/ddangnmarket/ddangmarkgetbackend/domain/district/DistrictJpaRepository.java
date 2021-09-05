package com.ddangnmarket.ddangmarkgetbackend.domain.district;

import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DistrictJpaRepository {
    private final EntityManager em;

    public District save(District district){
        em.persist(district);
        return district;
    }

    public List<District> findAll(){
        return em.createQuery("select d from District d", District.class)
                .getResultList();
    }

    public District findByDong(Dong dong){
        return em.createQuery("select d from District d" +
                " where d.dong = :dong", District.class)
                .setParameter("dong", dong)
                .getResultStream()
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소입니다."));
    }
}
