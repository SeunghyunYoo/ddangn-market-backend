package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;


@SpringBootTest
@Transactional
class AccountTest {

    @Autowired
    EntityManager em;
    @Autowired
    DistrictJpaRepository districtJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;


    @Test
    @Rollback(value = false)
    void activityTest(){

        District gumi = districtJpaRepository.findByDong(Dong.GUMI);
        District jeongja = districtJpaRepository.findByDong(Dong.JEONGJA1);

        Account account = new Account("test01", "000-0000-0000", "test01@gmail.com", "00000000");
        em.persist(account);
        ActivityArea activeArea1 = ActivityArea.createActiveArea(gumi, 0);
        ActivityArea activeArea2 = ActivityArea.createActiveArea(jeongja, 0);


    }

}