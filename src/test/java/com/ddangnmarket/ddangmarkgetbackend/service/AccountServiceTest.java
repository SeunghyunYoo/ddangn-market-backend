package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.dto.AccountUpdateDto;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
//@Rollback(value = false)
class AccountServiceTest {

    @Autowired AccountService accountService;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 회원가입(){
        Account account1 = new Account("account1", "000-0000-0000", "acc1@gmail.com");

        Account registerAccount = accountService.signUp(account1);

        em.flush();
        em.clear();

        Optional<Account> byId = accountJpaRepository.findById(registerAccount.getId());
        assertThat(byId.isPresent()).isTrue();

    }

    @Test
    void 이미존재하는이메일로회원가입(){
        Account account1 = new Account("account1", "000-0000-0000", "acc1@gmail.com");

        Account registerAccount = accountService.signUp(account1);

        Account account2 = new Account("account1", "000-0000-0000", "acc1@gmail.com");
        assertThrows(IllegalStateException.class, () -> accountService.signUp(account2),
                "이미 존재하는 회원입니다.");
    }

    @Test
    void 회원정보업데이트(){
        Account account1 = new Account("account1", "000-0000-0000", "acc1@gmail.com");
        accountService.signUp(account1);

        AccountUpdateDto accountUpdateDto = new AccountUpdateDto();
        accountUpdateDto.setMail("acc2@gmail.com");
        accountService.updateInfo(account1, accountUpdateDto);

        em.flush();
        em.clear();

        assertThat(accountJpaRepository.findByMail("acc2@gmail.com").isPresent()).isTrue();

    }


}