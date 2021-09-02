package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.PostStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class PostJpaRepositoryTest {

    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostRepository postRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;
    @Autowired
    EntityManager em;

    private final static int initPostCount = 0;

    @Test
    void 게시글저장및삭제(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);

        em.flush();
        em.clear();

        // 1. 등록
        Optional<Post> byId = postJpaRepository.findById(post1.getId());
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getTitle()).isEqualTo(post1.getTitle());
        assertThat(byId.get().getSeller().getNickname()).isEqualTo(account1.getNickname());
        assertThat(byId.get().getPostCategory().getCategory().getCategoryTag()).isEqualTo(DIGITAL);

        // 2. 삭제
        postJpaRepository.delete(byId.get());
        assertThat(postJpaRepository.findById(post1.getId()).isEmpty()).isTrue();
    }

    @Test
    void 전체게시글조회(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);

        em.flush();
        em.clear();

        List<Post> posts = postJpaRepository.findAll();
        assertThat(posts.size()).isEqualTo(2 + initPostCount);
    }

    @Test
    void 카테고리별로조회(){
//        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
//        em.persist(account1);
//
//        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
//        Category book = categoryJpaRepository.findByCategoryTag(BOOK);
//
//        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
//        postJpaRepository.save(post1);
//        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
//        postJpaRepository.save(post2);
//
//        em.flush();
//        em.clear();
//
//        List<Post> allByCategory = postJpaRepository.findAllByCategory(DIGITAL);
//        assertThat(allByCategory.size()).isEqualTo(1);
//        assertThat(allByCategory.get(0).getSeller().getNickname()).isEqualTo("account01");

    }

    @Test
    void 판매자로조회(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);

        em.flush();
        em.clear();

        List<Post> allBySeller = postJpaRepository.findAllBySeller(account1);
        assertThat(allBySeller.size()).isEqualTo(2);

    }

    @Test
    void 상태별조회(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);
        Post post3 = Post.createPost("JPA 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post3);

        post1.setPostStatus(RESERVE);
        post2.setPostStatus(COMPLETE);

        em.flush();
        em.clear();

        assertThat(postJpaRepository.findAllByStatus(NEW).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllByStatus(RESERVE).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllByStatus(COMPLETE).size()).isEqualTo(1);
    }

    @Test
    void 카테고리그리고상태별로조회(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);
        Post post3 = Post.createPost("JPA 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post3);

        post1.setPostStatus(RESERVE);
        post2.setPostStatus(COMPLETE);

        em.flush();
        em.clear();

        assertThat(postJpaRepository.findAllByCategoryAndStatus(DIGITAL, RESERVE).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllByCategoryAndStatus(BOOK, COMPLETE).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllByCategoryAndStatus(BOOK, NEW).size()).isEqualTo(1);


    }

    @Test
    void 판매자그리고상태로조회(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        em.persist(account1);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);
        Category book = categoryJpaRepository.findByCategoryTag(BOOK);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, new PostCategory(digital), account1);
        postJpaRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post2);
        Post post3 = Post.createPost("JPA 책 판매", "스프링 책 팔아요", 10000, new PostCategory(book), account1);
        postJpaRepository.save(post3);

        post1.setPostStatus(RESERVE);
        post2.setPostStatus(COMPLETE);

        em.flush();
        em.clear();

        assertThat(postJpaRepository.findAllBySellerAndStatus(account1, NEW).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllBySellerAndStatus(account1, RESERVE).size()).isEqualTo(1);
        assertThat(postJpaRepository.findAllBySellerAndStatus(account1, COMPLETE).size()).isEqualTo(1);
    }

    @Test
    void test(){

        List<PostStatus> aNew = List.of(NEW, RESERVE);
        List<Post> statuses = em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.postCategory pc" +
                        " join fetch pc.category c" +
                        " where p.postStatus in (:statuses)", Post.class)
                .setParameter("statuses", aNew)
                .getResultList();

        System.out.println("statuses = " + statuses.size());
    }
}