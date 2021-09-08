package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {



    Optional<Account> findByMail(String mail);

    Optional<Account> findByNickname(String nickname);

    @Query("select a from Account a" +
            " join fetch a.activityArea ar" +
            " join fetch ar.district d" +
            " where a.id = :id")
    Optional<Account> findWithActivityAreaById(@Param("id") Long id);

}
