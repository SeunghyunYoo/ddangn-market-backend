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


    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    Optional<Post> findById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"sale", "purchase"})
    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    Optional<Post> findWithSaleAndPurchaseById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"seller"})
    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    Optional<Post> findWithSellerById(@Param("id") Long id);

    @Query("select p from Post p where p.district in :districts and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAll(@Param("districts") Collection<District> districts);

    @Query("select p from Post p where p.district in :districts and p.postStatus = :postStatus and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAllByPostStatus(
            @Param("districts") Collection<District> districts, @Param("postStatus") PostStatus postStatus);


    @Query("select p from Post p where p.district in :districts and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category", "sale", "purchase"})
    List<Post> findAllByPostStatuses(
            @Param("districts") Collection<District> districts, @Param("postStatuses") Collection<PostStatus> postStatuses
    );

    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    @Query("select p from Post p where p.seller = :seller and p.isDeleted = false")
    List<Post> findAllBySeller(@Param("seller") Account seller);

    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    @Query("select p from Post p" +
            " where p.seller = :seller and p.postStatus = :postStatus" +
            " and p.isDeleted = false")
    List<Post> findAllBySellerAndPostStatus(
            @Param("seller") Account seller, @Param("postStatus") PostStatus postStatus);

    @Query("select p from Post p where p.seller in :seller and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"category", "district", "sale", "purchase"})
    List<Post> findAllBySellerAndPostStatuses(
            @Param("seller") Account seller, @Param("postStatuses") Collection<PostStatus> postStatuses);

    @Query("select p from Post p where p.district in :districts and p.category.categoryTag = :categoryTag and p.isDeleted = false")

    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategory(
            @Param("districts") Collection<District> districts, @Param("categoryTag") CategoryTag categoryTag);


    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.postStatus = :postStatus and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategoryAndPostStatus(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag, @Param("postStatus") PostStatus postStatus);

    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "sale", "purchase"})
    List<Post> findAllByCategoryAndPostStatuses(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag,
            @Param("postStatuses") Collection<PostStatus> postStatuses);

    @Query("select p from Post p" +
            " join fetch p.district d" +
            " join fetch p.category c" +
            " join fetch p.seller s" +
            " join fetch p.purchase pc" +
            " join fetch p.sale sl" +
            " where pc.account = :buyer")
    List<Post> findAllPurchase(@Param("buyer") Account buyer);
}
