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

    public List<Post> findAllByStatus(List<District> districts, PostStatus postStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.category c" +
                        " where p.district in (:districts)" +
                        " and p.postStatus = :status", Post.class)
                .setParameter("districts", districts)
                .setParameter("status", postStatus)
                .getResultList();
    }

    public List<Post> findAllByStatuses(List<District> districts, List<PostStatus> postStatuses){
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

    public List<Post> findAllBySellerAndStatus(Account seller, PostStatus postStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.category c" +
                        " join fetch p.district d" +
                        " where p.seller = :seller" +
                        " and p.postStatus = :status", Post.class)
                .setParameter("seller", seller)
                .setParameter("status", postStatus)
                .getResultList();
    }

    public List<Post> findAllBySellerAndStatuses(Account seller, List<PostStatus> postStatuses) {
        return em.createQuery("select p from Post p" +
                " join fetch p.category c" +
                " join fetch p. district d" +
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

    public List<Post> findAllByCategoryAndStatus(List<District> districts, CategoryTag categoryTag, PostStatus postStatus){

        return em.createQuery("select p from Post p" +
                " where p.district in (:districts)" +
                " and p.category.categoryTag = :categoryTag" +
                " and p.postStatus = :status", Post.class)
                .setParameter("districts", districts)
                .setParameter("categoryTag", categoryTag)
                .setParameter("status", postStatus)
                .getResultList();
    }

    public List<Post> findAllByCategoryAndStatuses(List<District> districts, CategoryTag categoryTag, List<PostStatus> postStatuses) {
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
}
