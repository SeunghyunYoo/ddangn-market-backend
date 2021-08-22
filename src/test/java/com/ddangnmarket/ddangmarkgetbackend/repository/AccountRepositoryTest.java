package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
//@Rollback(value = false)
class AccountRepositoryTest {

    @Autowired AccountJpaRepository accountJpaRepository;
    @Autowired AccountRepository accountRepository;
    @Autowired
    EntityManager em;

    @Test
    void saveTest(){

        Account account = new Account("account1", "000-0000-0000", "acc1@gmail.com");

        Account saveAccount = accountJpaRepository.save(account);

        em.flush();
        em.clear();

        Optional<Account> byId = accountJpaRepository.findById(saveAccount.getId());
        assertThat(byId.isPresent()).isTrue();

    }

    @Test
    void findByMailTest(){
        Account account = new Account("account1", "000-0000-0000", "acc1@gmail.com");

        Account saveAccount = accountJpaRepository.save(account);

        em.flush();
        em.clear();

        Optional<Account> byId = accountJpaRepository.findById(saveAccount.getId());
        assertThat(byId.isPresent()).isTrue();

        Optional<Account> byMail = accountJpaRepository.findByMail(saveAccount.getMail());
        assertThat(byMail.orElseThrow()).isEqualTo(byId.get());
        assertThat(byMail.get().getNickname()).isEqualTo(account.getNickname());
        assertThat(byMail.get().getPhone()).isEqualTo(account.getPhone());
        assertThat(byMail.get().getMail()).isEqualTo(account.getMail());

        assertThat(accountJpaRepository.existsByMail(byId.get().getMail())).isTrue();
    }

    @Test
    void 가입하지않은회원체크(){
        Optional<Account> byMail = accountJpaRepository.findByMail("acc@gmail.com");
        assertThat(byMail).isEmpty();

        boolean existsByMail = accountJpaRepository.existsByMail("acc@gmail.com");
        assertThat(existsByMail).isFalse();

//        Optional<Account> byMail = accountRepository.findByMail("acc@gmail.com");
    }
}