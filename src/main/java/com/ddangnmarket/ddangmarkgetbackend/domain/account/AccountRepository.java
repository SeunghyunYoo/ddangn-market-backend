package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByMail(String mail);

    boolean existsByNickname(String nickname);

    Optional<Account> findByMail(String mail);

    Account findByNickname(String nickname);

}
