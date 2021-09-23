package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Category;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    // TODO sale, purchase 왜 같이 조회를 하는지
    // -> toOne 관계는 대상 테이블에 외래키가 있을 시, 프록시 기능의 한계로 항상 즉시 로딩

    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category", "district"})
    Optional<Post> findAllInfoById(@Param("id") Long id);


    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    Optional<Post> findWithSaleAndPurchaseById(@Param("id") Long id);

    @EntityGraph(attributePaths = {"seller"})
    @Query("select p from Post p where p.id = :id and p.isDeleted = false")
    Optional<Post> findWithSellerById(@Param("id") Long id);

    @Query("select p from Post p where p.district in :districts and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category"})
    List<Post> findAll(@Param("districts") Collection<District> districts);

    @Query("select p from Post p where p.district in :districts and p.postStatus = :postStatus and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category"})
    List<Post> findAllByPostStatus(
            @Param("districts") Collection<District> districts, @Param("postStatus") PostStatus postStatus);


    @Query("select p from Post p where p.district in :districts and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller", "category"})
    List<Post> findAllByPostStatuses(
            @Param("districts") Collection<District> districts, @Param("postStatuses") Collection<PostStatus> postStatuses
    );

    @EntityGraph(attributePaths = {"category", "district"})
    @Query("select p from Post p where p.seller = :seller and p.isDeleted = false")
    List<Post> findAllBySeller(@Param("seller") Account seller);

    @Query("select p from Post p where p.seller = :seller and p.isDeleted = false")
    List<Post> findAllIdsBySeller(@Param("seller") Account seller);

    @EntityGraph(attributePaths = {"category", "district"})
    @Query("select p from Post p" +
            " where p.seller = :seller and p.postStatus = :postStatus" +
            " and p.isDeleted = false")
    List<Post> findAllBySellerAndPostStatus(
            @Param("seller") Account seller, @Param("postStatus") PostStatus postStatus);

    @Query("select p from Post p where p.seller in :seller and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"category", "district"})
    List<Post> findAllBySellerAndPostStatuses(
            @Param("seller") Account seller, @Param("postStatuses") Collection<PostStatus> postStatuses);

    @Query("select p from Post p where p.district in :districts and p.category.categoryTag = :categoryTag and p.isDeleted = false")

    @EntityGraph(attributePaths = {"seller"})
    List<Post> findAllByCategory(
            @Param("districts") Collection<District> districts, @Param("categoryTag") CategoryTag categoryTag);


    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.postStatus = :postStatus and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller"})
    List<Post> findAllByCategoryAndPostStatus(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag, @Param("postStatus") PostStatus postStatus);

    @Query("select p from Post p where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.postStatus in :postStatuses and p.isDeleted = false")
    @EntityGraph(attributePaths = {"seller"})
    List<Post> findAllByCategoryAndPostStatuses(
            @Param("districts") Collection<District> districts,
            @Param("categoryTag") CategoryTag categoryTag,
            @Param("postStatuses") Collection<PostStatus> postStatuses);

//    @Query("select p from Post p" +
//            " join fetch p.district d" +
//            " join fetch p.category c" +
//            " join fetch p.seller s" +
//            " where pc.account = :buyer and p.isDeleted = false")
//    List<Post> findAllPurchase(@Param("buyer") Account buyer);


    @Query("select p from Post p" +
            " where p.district in :districts" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category", "seller"})
    Page<Post> findPagePosts(
            Pageable pageable,
            @Param("districts") Collection<District> districts);

    @Query("select p from Post p" +
            " where p.postStatus in :postStatuses" +
            " and p.district in :districts" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category", "seller"})
    Page<Post> findPagePostsByStatuses(
            Pageable pageable,
            @Param("postStatuses") Collection<PostStatus> postStatuses,
            @Param("districts") Collection<District> districts);

    @Query("select p from Post p" +
            " where p.seller = :seller" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category"})
    Page<Post> findPagePostsBySeller(
            Pageable pageable,
            @Param("seller") Account seller);

    @Query("select p from Post p" +
            " where p.postStatus in :postStatuses" +
            " and p.seller = :seller" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category"})
    Page<Post> findPagePostsBySellerAndStatuses(
            Pageable pageable,
            @Param("seller") Account seller,
            @Param("postStatuses") Collection<PostStatus> postStatuses);

    @Query("select p from Post p" +
            " where p.district in :districts" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category", "seller"})
    Page<Post> findPagePostsByCategory(
            Pageable pageable,
            @Param("categoryTag") CategoryTag categoryTag,
            @Param("districts") Collection<District> districts);

    @Query("select p from Post p" +
            " where p.district in :districts" +
            " and p.postStatus in :postStatuses" +
            " and p.category.categoryTag = :categoryTag" +
            " and p.isDeleted = false")
    @EntityGraph(attributePaths = {"district", "category", "seller"})
    Page<Post> findPagePostsByCategoryAndStatuses(
            Pageable pageable,
            @Param("categoryTag") CategoryTag categoryTag,
            @Param("postStatuses") Collection<PostStatus> postStatuses,
            @Param("districts") Collection<District> districts);
}
