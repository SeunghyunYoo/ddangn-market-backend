package com.ddangnmarket.ddangmarkgetbackend.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class AccountServiceTest {

    @Autowired AccountService accountService;


    @Test
    void 회원가입(){

        Account account1 = new Account("account1", "010-0000-0000", "account1@gmail.com");

        Account registerAccount = accountService.register(account1);
        assertThat(registerAccount.getMail()).isEqualTo(account1.getMail());
    }

    @Test
    @Rollback
    void 중복이메일(){
        Account account1 = new Account("account1", "010-0000-0000", "account1@gmail.com");
        Account account2 = new Account("account2", "010-0000-0000", "account1@gmail.com");

        Account registerAccount = accountService.register(account1);

        assertThrows(IllegalStateException.class, () -> accountService.register(account2), "이 이메일을 사용할 수 없습니다.");
    }
}