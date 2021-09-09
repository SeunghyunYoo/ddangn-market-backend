package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long>, InterestRepositoryCustom {

    Optional<Interest> findByIdAndAccount(Long id, Account account);

    Optional<Interest> findByAccountAndPostId(Account account, Long PostId);

    @Query("select i from Interest i" +
            " join fetch i.post p" +
            " join fetch p.seller s" +
            " join fetch p.category c" +
            " join fetch i.account a" +
            " where a = :account" +
            " and i.isDeleted = false")
    List<Interest> findAllByAccount(@Param("account") Account account);
}
