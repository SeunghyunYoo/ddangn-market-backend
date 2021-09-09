package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PurchaseTest {

    @Autowired
    EntityManager em;

    @Test
    void purchaseTest(){

//        Account seller = new Account();
//        seller.setNickname("seller");
//        Account buyer = new Account();
//        buyer.setNickname("buyer");
//        em.persist(seller);
//        em.persist(buyer);
//
//
//        Post post1 = new Post();
//        post1.setSeller(seller);
//        post1.setTitle("맥북 판매");
//        post1.setStatus(Status.NEW);
//        em.persist(post1);
//
//        Post post2 = new Post();
//        post2.setSeller(seller);
//        post2.setTitle("맥미니 판매");
//        post2.setStatus(Status.NEW);
//        em.persist(post2);
//
//        em.flush();
//        em.clear();
//
//        Post findPost1 = em.find(Post.class, post1.getId());
//        Post findPost2 = em.find(Post.class, post2.getId());
//
//        System.out.println(findPost1.getTitle());
//        System.out.println(findPost1.getSeller().getNickname());
//
//        Purchase purchase1 = purchase(buyer, findPost1);
//        Purchase purchase2 = purchase(buyer, findPost2);
//
//        em.persist(purchase1);
//        em.persist(purchase2);
//
//        em.flush();
//        em.clear();
//
//        Purchase findPurchase = em.find(Purchase.class, purchase1.getId());
//
//        System.out.println(findPurchase.getPost().getTitle());
//        System.out.println(findPurchase.getPost().getSeller().getNickname());
//        System.out.println(findPurchase.getAccount().getNickname());

    }

    private Purchase purchase(Account buyer, Post findPost) {
        findPost.setPostStatus(PostStatus.NEW);
        Purchase purchase = new Purchase();
        purchase.setPost(findPost);
        purchase.setAccount(buyer);
        return purchase;
    }

}