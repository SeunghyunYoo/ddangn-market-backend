package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InterestRepository extends JpaRepository<Interest, Long>, InterestRepositoryCustom {

    Optional<Interest> findByIdAndAccount(Long id, Account account);

    Optional<Interest> findByAccountAndPostId(Account account, Long PostId);

    // TODO 쿼리 최적화 필요 sale, purchase 등 불필요한 정보
    //  + 왜 sale, purchase join하면 출력이 안되는지
    @Query("select i from Interest i" +
            " join fetch i.post p" +
            " join fetch p.seller s" +
            " join fetch i.account a" +
            " join fetch p.category c" +
            " join fetch p.district d" +
            " where a = :account" +
            " and i.isDeleted = false and p.isDeleted = false")
    List<Interest> findAllByAccount(@Param("account") Account account);


    @Query(value = "select i from Interest i" +
            " join fetch i.post p" +
            " join fetch p.seller s" +
            " join fetch p.category c" +
            " join fetch i.account a" +
            " join fetch p.district d" +
            " where a = :account" +
            " and i.isDeleted = false and p.isDeleted = false",
            countQuery = "select count(i.id) from Interest i" +
                    " join i.post p" +
                    " where i.account =: account" +
                    " and i.isDeleted = false and p.isDeleted = false")
    Page<Interest> findInterestsByAccount(Pageable pageable, @Param("account") Account account);
}
