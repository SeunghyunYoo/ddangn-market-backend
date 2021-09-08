package com.ddangnmarket.ddangmarkgetbackend.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountServiceV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class AccountServiceTest {

    @Autowired
    AccountServiceV1 accountServiceV1;


    @Test
    void 회원가입(){

        Account account1 = new Account("account01", "010-0000-0000", "account01@gmail.com", "00000000");

//        Account registerAccount = accountService.signUp(account1);
//        assertThat(registerAccount.getMail()).isEqualTo(account1.getMail());
    }

    @Test
//    @Rollback
    void 중복이메일(){
        Account account1 = new Account("account01", "010-0000-0000", "account01@gmail.com", "00000000");
        Account account2 = new Account("account02", "010-0000-0000", "account01@gmail.com", "00000000");

//        Account registerAccount = accountService.signUp(account1);

//        assertThrows(DuplicateEmailException.class, () -> accountService.signUp(account2), "이 이메일을 사용할 수 없습니다.");
    }
}