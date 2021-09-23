package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag.DIGITAL;
import static com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    PostRepository postRepository;
    @Autowired
    DistrictRepository districtRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;
    @Autowired
    AccountRepository accountRepository;

    @Test
    void findAll(){
        //given
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        Account account2= new Account("account02", "000-0000-0000", "account02@gmail.com", "00000000");

        ActivityArea activeArea1 = ActivityArea.createActiveArea(districtRepository.findByDong(YATAP1), 0);
        ActivityArea activeArea2 = ActivityArea.createActiveArea(districtRepository.findByDong(YATAP3), 0);

        account1.setActivityArea(activeArea1);
        account2.setActivityArea(activeArea2);

        em.persist(account1);
        em.persist(account2);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);

        Post post1 = Post.createPost("맥북판매", "맥북팔아요", 100000, digital, account1);
        postRepository.save(post1);
        Post post2 = Post.createPost("스프링 책 판매", "스프링 책 팔아요", 10000, digital, account1);
        postRepository.save(post2);
        Post post3 = Post.createPost("티셔츠 판매 ", "티셔츠 팔아요", 100000, digital, account2);
        postRepository.save(post3);
        Post post4 = Post.createPost("책상 판매", "책상 팔아요", 10000, digital, account2);
        postRepository.save(post4);

        //when
        District yatap1 = districtRepository.findByDong(YATAP1);
        District yatap3 = districtRepository.findByDong(YATAP3);

        List<Post> postsYatap1 = postRepository.findAll(List.of(yatap1));
        List<Post> postsYatap3 = postRepository.findAll(List.of(yatap3));
        List<Post> postsBoth = postRepository.findAll(List.of(yatap1, yatap3));

        //then
        assertThat(postsYatap1.size()).isEqualTo(2);
        assertThat(postsYatap3.size()).isEqualTo(2);
        assertThat(postsBoth.size()).isEqualTo(4);


    }

    @Test
    void pagingTest(){
        Account account1 = new Account("account01", "000-0000-0000", "account01@gmail.com", "00000000");
        Account account2= new Account("account02", "000-0000-0000", "account02@gmail.com", "00000000");

        ActivityArea activeArea1 = ActivityArea.createActiveArea(districtRepository.findByDong(YATAP1), 0);
        ActivityArea activeArea2 = ActivityArea.createActiveArea(districtRepository.findByDong(YATAP3), 0);

        account1.setActivityArea(activeArea1);
        account2.setActivityArea(activeArea2);

        em.persist(account1);
        em.persist(account2);

        em.flush();
        em.clear();


        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);

        System.out.println("=================");
        PageRequest pageRequest = PageRequest.of(0, 3);

        District gumi = districtRepository.findByDong(GUMI);

        Page<Post> pagePosts = postRepository.findPagePosts(pageRequest, List.of(gumi));
        int totalPages = pagePosts.getTotalPages();
        long totalElements = pagePosts.getTotalElements();
        List<Post> content = pagePosts.getContent();
        int size = pagePosts.getSize();
        Pageable pageable = pagePosts.getPageable();

        System.out.println("=================");

        Account account = accountRepository.findByNickname("account01").orElseThrow();

        Page<Post> pagePostsByCategory = postRepository.findPagePostsByCategory(pageable, DIGITAL, List.of(gumi));

        System.out.println();
    }


}