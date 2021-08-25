package com.ddangnmarket.ddangmarkgetbackend.post;

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

    public List<Post> findAll(){
        return em.createQuery("select p from Post p", Post.class).getResultList();
    }

    public List<Post> findAllByCategory(CategoryTag categoryTag){
        return em.createQuery("select p from Post p" +
                " where p.categoryTag = :categoryTag", Post.class)
                .setParameter("categoryTag", categoryTag)
                .getResultList();
    }

    public List<Post> findAllBySeller(Account seller){
        return em.createQuery("select p from Post p" +
                " where p.seller = :seller", Post.class)
                .setParameter("seller", seller)
                .getResultList();
    }

    public List<Post> findAllByStatus(Status status){
        return em.createQuery("select p from Post p" +
                " where p.status = :status", Post.class)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Post> findAllByCategoryTagAndStatus(CategoryTag categoryTag, Status status){
        return em.createQuery("select p from Post p" +
                " where p.categoryTag = :categoryTag" +
                " and p.status = :status", Post.class)
                .setParameter("categoryTag", categoryTag)
                .setParameter("status", status)
                .getResultList();
    }

    public List<Post> findAllBySellerAndStatus(Account seller, Status status){
        return em.createQuery("select p from Post p" +
                " where p.seller = :seller" +
                " and p.status = :status", Post.class)
                .setParameter("seller", seller)
                .setParameter("status", status)
                .getResultList();
    }
}
