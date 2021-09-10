package com.ddangnmarket.ddangmarkgetbackend.domain.sale;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    List<Sale> findByAccount(Account account);

    long countByAccount(Account account);

    @Query("select avg(s.score) from Sale s where s.account = :account")
    Optional<Double> findAvgScoreByAccount(@Param("account") Account account);
}
