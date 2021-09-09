package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostJpaRepository {

    private final EntityManager em;

    public Post save(Post post){
        em.persist(post);
        return post;
    }

    public void delete(Post post){
        em.remove(post);
    }

    public Optional<Post> findById(Long id){
        return Optional.ofNullable(em.find(Post.class, id));
    }

    public Optional<Post> findWithSaleAndPurchaseById(Long id){
        return em.createQuery("select p from Post p" +
                " join fetch p.purchase pc" +
                " join fetch p.sale s" +
                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();
    }

    public Optional<Post> findWithSellerById(Long id){
        return em.createQuery("select p from Post p" +
                " join fetch p.seller s" +
                " join fetch p.district d" +
                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();
    }

    public List<Post> findAll(List<District> districts){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)", Post.class)
                .setParameter("districts", districts)
                .getResultList();
    }

    public List<Post> findAllBySaleStatus(List<District> districts, PostStatus postStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)" +
                        " and p.postStatus = :postStatus", Post.class)
                .setParameter("districts", districts)
                .setParameter("postStatus", postStatus)
                .getResultList();
    }


    public List<Post> findAllBySaleStatuses(List<District> districts, List<PostStatus> postStatuses){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)" +
                        " and p.postStatus in (:postStatuses)", Post.class)
                .setParameter("districts", districts)
                .setParameter("postStatuses", postStatuses)
                .getResultList();
    }

    public List<Post> findAllBySeller(Account seller){
        return em.createQuery("select p from Post p" +
                        " join fetch p.category c" +
                        " join fetch p.district d" +
                        " where p.seller = :seller", Post.class)
                .setParameter("seller", seller)
                .getResultList();
    }

    public List<Post> findAllBySellerAndSaleStatus(Account seller, PostStatus postStatus){

        return em.createQuery("select p from Post p" +
                        " join fetch p.category c" +
                        " join fetch p.district d" +
                        " where p.seller = :seller" +
                        " and p.postStatus = :postStatus", Post.class)
                .setParameter("seller", seller)
                .setParameter("postStatus", postStatus)
                .getResultList();
    }

    public List<Post> findAllBySellerAndSaleStatuses(Account seller, List<PostStatus> postStatuses) {

        return em.createQuery("select p from Post p" +
                " join fetch p.category c" +
                " join fetch p.district d" +
                " where p.seller =: seller" +
                " and p.postStatus in (:postStatuses)", Post.class)
                .setParameter("seller", seller)
                .setParameter("postStatuses", postStatuses)
                .getResultList();
    }

    public List<Post> findAllByCategory(List<District> districts, CategoryTag categoryTag){

        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " where p.district in(:districts)" +
                        " and p.category.categoryTag = :categoryTag", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .getResultList();
    }

    public List<Post> findAllByCategoryAndSaleStatus(List<District> districts, CategoryTag categoryTag, PostStatus postStatus){

        return em.createQuery("select p from Post p" +
                " where p.district in (:districts)" +
                " and p.category.categoryTag = :categoryTag" +
                " and p.postStatus = :postStatus", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .setParameter("postStatus", postStatus)
                .getResultList();
    }

    public List<Post> findAllByCategoryAndSaleStatuses(List<District> districts, CategoryTag categoryTag, List<PostStatus> postStatuses) {

        return em.createQuery("select p from Post p" +
                " join fetch p.seller s" +
                " where p.district in (:districts)" +
                " and p.category.categoryTag = :categoryTag" +
                " and p.postStatus in (:postStatuses)", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .setParameter("postStatuses", postStatuses)
                .getResultList();
    }

    public List<Post> findAllPurchase(Account buyer){
        return em.createQuery("select p from Post p" +
                " join fetch p.district d" +
                " join fetch p.category c" +
                " join fetch p.seller s" +
                " join fetch p.purchase pc" +
                " where pc.account = :buyer", Post.class)
                .setParameter("buyer", buyer)
                .getResultList();
    }
}
