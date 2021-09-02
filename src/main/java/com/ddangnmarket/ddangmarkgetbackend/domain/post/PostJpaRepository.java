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

    public List<Post> findAll(){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c", Post.class)
                .getResultList();
    }

    public List<Post> findAllByStatus(PostStatus postStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c" +
                        " where p.postStatus = :status", Post.class)
                .setParameter("status", postStatus)
                .getResultList();
    }

    public List<Post> findAllByStatuses(List<PostStatus> postStatuses){
        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c" +
                        " where p.postStatus in (:statuses)", Post.class)
                .setParameter("statuses", postStatuses)
                .getResultList();
    }

    public List<Post> findAllBySeller(Account seller){
        return em.createQuery("select p from Post p" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c" +
                        " where p.seller = :seller", Post.class)
                .setParameter("seller", seller)
                .getResultList();
    }

    public List<Post> findAllBySellerAndStatus(Account seller, PostStatus postStatus){
        return em.createQuery("select p from Post p" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c" +
                        " where p.seller = :seller" +
                        " and p.postStatus = :status", Post.class)
                .setParameter("seller", seller)
                .setParameter("status", postStatus)
                .getResultList();
    }

    public List<Post> findAllByCategory(Category category){

        return em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                " where p.postCategory.category = :category", Post.class)
                .setParameter("category", category)
                .getResultList();
    }

    public List<Post> findAllByCategoryAndStatus(Category category, PostStatus postStatus){

        return em.createQuery("select p from Post p" +
                " where p.postCategory.category = :category" +
                " and p.postStatus = :status", Post.class)
                .setParameter("category", category)
                .setParameter("status", postStatus)
                .getResultList();
    }
}
