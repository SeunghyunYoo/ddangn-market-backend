package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.Status.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@Rollback(value = false)
class PostJpaRepositoryTest {

    @Autowired PostJpaRepository postJpaRepository;
    @Autowired AccountJpaRepository accountJpaRepository;
    @Autowired PostRepository postRepository;
    @Autowired CategoryJpaRepository categoryJpaRepository;
    @Autowired
    EntityManager em;


    @Test
    void 게시글저장(){
        Account account1 = new Account("account1");
        em.persist(account1);
        Post post1 = new Post("title", account1);
        postJpaRepository.save(post1);

        em.flush();
        em.clear();

        Optional<Post> byId = postJpaRepository.findById(post1.getId());
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getTitle()).isEqualTo(post1.getTitle());
        assertThat(byId.get().getSeller().getNickname()).isEqualTo(account1.getNickname());
    }

    @Test
    void 게시글삭제(){
        Account account1 = new Account("account1");
        em.persist(account1);
        Post post1 = new Post("title", account1);
        postJpaRepository.save(post1);

        postJpaRepository.delete(post1);

        em.clear();
        em.flush();

        Optional<Post> byId = postJpaRepository.findById(post1.getId());
        assertThat(byId.isEmpty()).isTrue();
    }

    @Test
    void 카테고리설정(){
        Account account1 = new Account("account1");
        em.persist(account1);

        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        postJpaRepository.save(post1);

        em.flush();
        em.clear();

        Optional<Post> byId = postJpaRepository.findById(post1.getId());
        assertThat(byId.isPresent()).isTrue();
        assertThat(byId.get().getTitle()).isEqualTo(post1.getTitle());
        assertThat(byId.get().getSeller().getNickname()).isEqualTo(account1.getNickname());
        assertThat(byId.get().getCategoryTag()).isEqualTo(DIGITAL);

        // postJpaRepository.delete(byId.get());

    }

    @Test
    void 전체게시글조회(){
        Account account1 = new Account("account1");
        em.persist(account1);
        Post post1 = new Post("title1", account1);
        Post post2 = new Post("title2", account1);
        postJpaRepository.save(post1);
        postJpaRepository.save(post2);

        em.flush();
        em.clear();

        List<Post> posts = postJpaRepository.findAll();
        assertThat(posts.size()).isEqualTo(2);

    }

    @Test
    void 카테고리별로조회(){
        Account account1 = new Account("account1");
        em.persist(account1);

        /*Category category1 = new Category();
        category1.setCategoryTag(DIGITAL);
        Category category2 = new Category();
        category2.setCategoryTag(CLOTHES);
        Category category3 = new Category();
        category3.setCategoryTag(APPLIANCE);
        em.persist(category1);
        em.persist(category2);
        em.persist(category3);*/


        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        Post post2 = new Post("title2", account1);
        post2.setCategoryTag(CLOTHES);
        Post post3 = new Post("title3", account1);
        post3.setCategoryTag(APPLIANCE);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);

        em.flush();
        em.clear();

        List<Post> posts = postJpaRepository.findAllByCategory(CLOTHES);
        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getCategoryTag()).isEqualTo(CLOTHES);
    }

    @Test
    void 판매자로조회(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);

        /*Category category1 = new Category();
        category1.setCategoryTag(DIGITAL);
        Category category2 = new Category();
        category2.setCategoryTag(CLOTHES);
        Category category3 = new Category();
        category3.setCategoryTag(APPLIANCE);*/

        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        Post post2 = new Post("title2", account1);
        post2.setCategoryTag(CLOTHES);
        Post post3 = new Post("title3", account2);
        post3.setCategoryTag(APPLIANCE);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);

        em.flush();
        em.clear();

        List<Post> allBySeller = postJpaRepository.findAllBySeller(account1);
        assertThat(allBySeller.size()).isEqualTo(2);
    }

    @Test
    void 상태별조회(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);

       /* Category category1 = new Category();
        category1.setCategoryTag(DIGITAL);
        Category category2 = new Category();
        category2.setCategoryTag(CLOTHES);
        Category category3 = new Category();
        category3.setCategoryTag(APPLIANCE);
        em.persist(category1);
        em.persist(category2);
        em.persist(category3);*/


        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        Post post2 = new Post("title2", account1);
        post2.setCategoryTag(CLOTHES);
        Post post3 = new Post("title3", account1);
        post3.setCategoryTag(APPLIANCE);
        post3.setStatus(COMPLETE);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);


        em.flush();
        em.clear();

        List<Post> newPost = postJpaRepository.findAllByStatus(NEW);
        List<Post> completePost = postJpaRepository.findAllByStatus(COMPLETE);
        List<Post> bookedPost;
        bookedPost = postJpaRepository.findAllByStatus(Status.RESERVE);

        assertThat(newPost.size()).isEqualTo(2);
        assertThat(completePost.size()).isEqualTo(1);
        assertThat(bookedPost.size()).isEqualTo(0);

    }

    @Test
    void 카테고리그리고상태별로조회(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);

        /*Category category1 = new Category();
        category1.setCategoryTag(DIGITAL);
        Category category2 = new Category();
        category2.setCategoryTag(CLOTHES);
        Category category3 = new Category();
        category3.setCategoryTag(APPLIANCE);
        em.persist(category1);
        em.persist(category2);
        em.persist(category3);*/


        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        Post post2 = new Post("title2", account1);
        post2.setCategoryTag(CLOTHES);
        Post post3 = new Post("title3", account1);
        post3.setCategoryTag(APPLIANCE);
        post3.setStatus(RESERVE);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);

        em.flush();
        em.clear();

        List<Post> compPosts = postJpaRepository.findAllByCategoryTagAndStatus(APPLIANCE, COMPLETE);
        List<Post> bookedPosts = postJpaRepository.findAllByCategoryTagAndStatus(APPLIANCE, RESERVE);
        List<Post> newPosts = postJpaRepository.findAllByCategoryTagAndStatus(APPLIANCE, NEW);

        assertThat(compPosts.size()).isEqualTo(0);
        assertThat(bookedPosts.size()).isEqualTo(1);
        assertThat(newPosts.size()).isEqualTo(0);

    }

    @Test
    void 판매자그리고상태로조회(){
        Account account1 = new Account("account1");
        Account account2 = new Account("account2");
        em.persist(account1);
        em.persist(account2);

        Post post1 = new Post("title", account1);
        post1.setCategoryTag(DIGITAL);
        Post post2 = new Post("title2", account1);
        post2.setCategoryTag(CLOTHES);
        Post post3 = new Post("title3", account1);
        post3.setCategoryTag(APPLIANCE);

        postJpaRepository.save(post1);
        postJpaRepository.save(post2);
        postJpaRepository.save(post3);

        em.flush();
        em.clear();

        List<Post> compPosts = postJpaRepository.findAllBySellerAndStatus(account1, COMPLETE);
        List<Post> bookedPosts = postJpaRepository.findAllBySellerAndStatus(account1, RESERVE);
        List<Post> newPosts = postJpaRepository.findAllBySellerAndStatus(account1, NEW);

        assertThat(compPosts.size()).isEqualTo(0);
        assertThat(bookedPosts.size()).isEqualTo(0);
        assertThat(newPosts.size()).isEqualTo(3);
    }
}