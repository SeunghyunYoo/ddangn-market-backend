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

    public Optional<Post> findByIdWithSaleAndPurchase(Long id){
        return em.createQuery("select p from Post p" +
                " join fetch p.purchase pc" +
                " join fetch p.sale s" +
                " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny();
    }

    public Optional<Post> findByIdWithSeller(Long id){
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

    public List<Post> findAllByStatus(List<District> districts, SaleStatus saleStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)" +
                        " and p.saleStatus = :status", Post.class)
                .setParameter("districts", districts)
                .setParameter("status", saleStatus)
                .getResultList();
    }

    public List<Post> findAllByStatuses(List<District> districts, List<SaleStatus> saleStatuses){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)" +
                        " and p.saleStatus in (:saleStatuses)", Post.class)
                .setParameter("districts", districts)
                .setParameter("saleStatuses", saleStatuses)
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

    public List<Post> findAllBySellerAndStatus(Account seller, SaleStatus saleStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.category c" +
                        " join fetch p.district d" +
                        " where p.seller = :seller" +
                        " and p.saleStatus = :status", Post.class)
                .setParameter("seller", seller)
                .setParameter("status", saleStatus)
                .getResultList();
    }

    public List<Post> findAllBySellerAndStatuses(Account seller, List<SaleStatus> saleStatuses) {
        return em.createQuery("select p from Post p" +
                " join fetch p.category c" +
                " join fetch p. district d" +
                " where p.seller =: seller" +
                " and p.saleStatus in (:saleStatuses)", Post.class)
                .setParameter("seller", seller)
                .setParameter("saleStatuses", saleStatuses)
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

    public List<Post> findAllByCategoryAndStatus(List<District> districts, CategoryTag categoryTag, SaleStatus saleStatus){

        return em.createQuery("select p from Post p" +
                " where p.district in (:districts)" +
                " and p.category.categoryTag = :categoryTag" +
                " and p.saleStatus = :status", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .setParameter("status", saleStatus)
                .getResultList();
    }

    public List<Post> findAllByCategoryAndStatuses(List<District> districts, CategoryTag categoryTag, List<SaleStatus> saleStatuses) {
        return em.createQuery("select p from Post p" +
                " join fetch p.seller s" +
                " where p.district in (:districts)" +
                " and p.category.categoryTag = :categoryTag" +
                " and p.saleStatus in (:saleStatuses)", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .setParameter("saleStatuses", saleStatuses)
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
