package com.ddangnmarket.ddangmarkgetbackend.domain.purchase;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    @Query("select pc from Purchase pc" +
            " join fetch pc.post p" +
            " join fetch p.category c" +
            " join fetch p.district d" +
            " join fetch p.seller s" +
            " where pc.account = : account")
    List<Purchase> findAllByAccount(@Param("account") Account account);
}
