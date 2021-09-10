package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a from Account a where a.id = :id and a.isDeleted = false")
//    @Query("select a from Account a where a.id = :id")
    Optional<Account> findById(@Param("id") Long id);

    @Query("select a from Account a where a.mail = :mail and a.isDeleted = false")
//    @Query("select a from Account a where a.mail = :mail")
    Optional<Account> findByMail(@Param("mail") String mail);

    @Query("select a from Account a where a.nickname = :nickname and a.isDeleted = false")
//    @Query("select a from Account a where a.nickname = :nickname")
    Optional<Account> findByNickname(@Param("nickname") String nickname);

    @Query("select a from Account a" +
            " join fetch a.activityArea ar" +
            " join fetch ar.district d" +
            " where a.id = :id and a.isDeleted = false")
    Optional<Account> findWithActivityAreaById(@Param("id") Long id);


}
