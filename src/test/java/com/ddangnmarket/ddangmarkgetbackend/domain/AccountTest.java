package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        District jeongja = districtJpaRepository.findByDong(Dong.JEONGJA);

        Account account = new Account("test01", "000-0000-0000", "test01@gmail.com", "00000000");
        em.persist(account);
        ActivityArea activeArea1 = ActivityArea.createActiveArea(gumi);
        ActivityArea activeArea2 = ActivityArea.createActiveArea(jeongja);
        account.addActivityArea(activeArea1);
        account.addActivityArea(activeArea2);
        em.persist(account);

        em.flush();
        em.clear();

        Account findAccount = accountJpaRepository.findById(account.getId()).orElseThrow();
        assertThat(findAccount.getActivityAreas().size()).isEqualTo(2);

        District gumi2 = districtJpaRepository.findByDong(Dong.GUMI);

        ActivityArea activityArea = findAccount.getActivityAreas()
                .stream().filter(area -> area.getDistrict().equals(gumi2))
                .findAny().orElseThrow();

        findAccount.removeActivityArea(activityArea);
        em.flush();
        em.clear();
        Account findAccount2 = accountJpaRepository.findById(account.getId()).orElseThrow();
        assertThat(findAccount2.getActivityAreas().size()).isEqualTo(1);

        ActivityArea activeArea11 = ActivityArea.createActiveArea(gumi);
        findAccount2.addActivityArea(activeArea11);

        em.flush();
        em.clear();
        Account findAccount3 = accountJpaRepository.findById(account.getId()).orElseThrow();

//        em.remove(findAccount2);

    }

}