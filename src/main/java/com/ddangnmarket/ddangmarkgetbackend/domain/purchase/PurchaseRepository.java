package com.ddangnmarket.ddangmarkgetbackend.domain.purchase;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select pc from Purchase pc" +
            " join fetch pc.post p" +
            " join fetch p.category c" +
            " join fetch p.district d" +
            " join fetch p.seller s" +
            " where pc.account = :account")
    List<Purchase> findAllByAccount(@Param("account") Account account);


    List<Purchase> findByAccount(Account account);

    long countByAccount(Account account);

    @Query("select avg(p.score) from Purchase p where p.account = :account")
    Optional<Double> findAvgScoreByAccount(@Param("account") Account account);
}
