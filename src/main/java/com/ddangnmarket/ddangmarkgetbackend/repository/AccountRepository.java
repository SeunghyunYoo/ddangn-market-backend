package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {

    boolean existsByMail(String mail);

    boolean existsByNickname(String nickname);

    Account findByMail(String mail);

    Account findByNickname(String nickname);

}
