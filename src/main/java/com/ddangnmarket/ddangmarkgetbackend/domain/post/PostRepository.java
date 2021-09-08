package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findById(Long id);

    @EntityGraph(attributePaths = {"sale", "purchase"})
    Optional<Post> findWithSaleAndPurchaseById(Long id);

    @EntityGraph(attributePaths = {"seller"})
    Optional<Post> findWithSellerById(Long id);

    @Query("select p from Post p where p.district in :districts")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAll(@Param("districts") Collection<District> districts);

    @Query("select p from Post p where p.district in :districts and p.saleStatus = :saleStatus")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAllBySaleStatus(
            @Param("districts") Collection<District> districts, @Param("saleStatus") SaleStatus saleStatus);


    @Query("select p from Post p where p.district in :districts and p.saleStatus in :saleStatuses")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAllBySaleStatuses(
            @Param("districts") Collection<District> districts, @Param("saleStatus") Collection<SaleStatus> saleStatus
    );

    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    List<Post> findAllBySeller(Account seller);

    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    List<Post> findAllBySellerAndSaleStatus(Account seller, SaleStatus saleStatus);

    @Query("select p from Post p where p.seller in :seller and p.saleStatus in :saleStatuses")
    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    List<Post> findAllBySellerAndSaleStatuses(
            @Param("seller") Account seller, @Param("saleStatuses") Collection<SaleStatus> saleStatuses);

    @Query("select p from Post p where p.district in :districts and p.category.categoryTag = :categoryTag")
    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategory(
            @Param("districts") Collection<District> districts, @Param("categoryTag") CategoryTag categoryTag);


    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.saleStatus = :saleStatus")
    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategoryAndSaleStatus(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag, @Param("saleStatus") SaleStatus saleStatus);


    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.saleStatus in :saleStatuses")
    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategoryAndSaleStatuses(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag,
            @Param("saleStatuses") Collection<SaleStatus> saleStatuses);


    @Query("select p from Post p" +
            " join fetch p.district d" +
            " join fetch p.category c" +
            " join fetch p.seller s" +
            " join fetch p.purchase pc" +
            " join fetch p.sale sl" +
            " where pc.account = :buyer")
    List<Post> findAllPurchase(@Param("buyer") Account buyer);
}
