package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ActivityArea;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.ChangeAccountPasswordRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.UpdateAccountInfoRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class AccountServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    AccountService accountService;
    @Autowired AccountRepository accountRepository;

    @Test
    void signUp(){
        Account account1 = new Account("test1", "000-0000-0000", "test1@gmail.com", "00000000");
        Account signUpAccount = accountService.signUp(account1, Dong.GUMI);

        em.flush();
        em.clear();

        Account findAccount = accountService.checkSessionAndFindAccount(signUpAccount.getId());
        assertThat(findAccount.getNickname()).isEqualTo(account1.getNickname());
    }

    @Test
    void activityArea(){
        Account account1 = new Account("test1", "000-0000-0000", "test1@gmail.com", "00000000");
        Account signUpAccount = accountService.signUp(account1, Dong.GUMI);

        em.flush();
        em.clear();

        Account findAccount = accountService.findAccountWithActivityArea(signUpAccount.getId());
        ActivityArea activityArea = findAccount.getActivityArea();
        assertThat(activityArea.getDistrict().getDong()).isEqualTo(Dong.GUMI);

        accountService.changeActivityArea(findAccount, Dong.YATAP1, 3);

        em.flush();
        em.clear();

        Dong changedDong = accountService.findAccountWithActivityArea(signUpAccount.getId())
                .getActivityArea().getDistrict().getDong();
        Integer changedRange = accountService.findAccountWithActivityArea(signUpAccount.getId())
                .getActivityArea().getRange();
        assertThat(changedDong).isEqualTo(Dong.YATAP1);
        assertThat(changedRange).isEqualTo(3);
    }

    @Test
    void changeInfo(){
        Account account1 = new Account("test1", "000-0000-0000", "test1@gmail.com", "00000000");
        Account signUpAccount = accountService.signUp(account1, Dong.GUMI);

        em.flush();
        em.clear();

        Account findAccount = accountService.checkSessionAndFindAccount(signUpAccount.getId());
        accountService.updateAccountInfo(findAccount,
                new UpdateAccountInfoRequestDto("test2", "010-0000-0000"));

        em.flush();
        em.clear();

        Account changedAccount = accountService.checkSessionAndFindAccount(signUpAccount.getId());
        assertThat(changedAccount.getNickname()).isEqualTo("test2");
        assertThat(changedAccount.getPhone()).isEqualTo("010-0000-0000");
    }

    @Test
    void changePassword(){
        Account account1 = new Account("test1", "000-0000-0000", "test1@gmail.com", "00000000");
        Account signUpAccount = accountService.signUp(account1, Dong.GUMI);

        em.flush();
        em.clear();

        Account findAccount = accountService.checkSessionAndFindAccount(signUpAccount.getId());
        accountService.changePassword(findAccount, new ChangeAccountPasswordRequestDto("11111111"));

        em.flush();
        em.clear();
        Account changedAccount = accountService.checkSessionAndFindAccount(signUpAccount.getId());
        assertThat(changedAccount.getPassword()).isEqualTo("11111111");
    }
}