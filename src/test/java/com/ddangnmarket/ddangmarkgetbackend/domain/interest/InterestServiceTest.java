package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
class InterestServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    AccountService accountService;
    @Autowired
    InterestService interestService;


    @Test
    void addInterest() {
        Account account1 = new Account("test1", "000-0000-0000", "test1@gmail.com", "00000000");
        Account signUpAccount = accountService.signUp(account1, Dong.GUMI);

        em.flush();
        em.clear();

        Account findAccount = accountService.findAccount(signUpAccount.getId());


    }

    @Test
    void findAllInterests() {
    }

    @Test
    void deleteInterest() {
    }
}