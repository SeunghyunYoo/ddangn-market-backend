package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.interest.InterestJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback(value = false)
class InterestJpaRepositoryTest {

    @Autowired
    InterestJpaRepository interestJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    CategoryJpaRepository categoryJpaRepository;
    @Autowired
    EntityManager em;

    private final String TEST_PHONE = "000-0000-0000";
    private final String TEST_EMAIL = "@gmail.com";
    private final String TEST_PASSWORD = "00000000";

    @Test
    void 관심상품등록및삭제(){
        Account account01 = new Account("test01", TEST_PHONE, "test01" + TEST_EMAIL, TEST_PASSWORD);
        em.persist(account01);
        Account account02 = new Account("test02", TEST_PHONE, "test02" + TEST_EMAIL, TEST_PASSWORD);
        em.persist(account02);

        Category digital = categoryJpaRepository.findByCategoryTag(DIGITAL);


        Post post1 = Post.createPost("title01", "desc01", 10000, digital, account01);
        em.persist(post1);
        Post post2 = Post.createPost("title02", "desc01", 10000, digital, account01);
        em.persist(post2);
        Post post3 = Post.createPost("title03", "desc01", 10000, digital, account01);
        em.persist(post3);
        Post post4 = Post.createPost("title04", "desc01", 10000, digital, account01);
        em.persist(post4);

        Interest interest1 = Interest.addInterest(post1, account02);
        Interest interest2 = Interest.addInterest(post2, account02);
        Interest interest3 = Interest.addInterest(post3, account02);
        Interest interest4 = Interest.addInterest(post4, account02);

        interestJpaRepository.save(interest1);
        interestJpaRepository.save(interest2);
        interestJpaRepository.save(interest3);

        em.flush();
        em.clear();

        List<Interest> allByAccount = interestJpaRepository.findAllByAccount(account02);

        Assertions.assertThat(allByAccount.size()).isEqualTo(4);


    }

    @Test
    void 게시글아이디로조회및삭제(){
//        Account seller = new Account("seller");
//        Account account = new Account("account");
//        em.persist(seller);
//        em.persist(account);
//
//
//        Post post1 = Post.post("맥북", seller, DIGITAL);
//        Post post2 = Post.post("그램", seller, DIGITAL);
//        Post post3 = Post.post("XPS", seller, DIGITAL);
//
//        em.persist(post1);
//        em.persist(post2);
//        em.persist(post3);
//
//
//        Interest interest1 = Interest.addInterest(post1, account);
//        Interest interest2 = Interest.addInterest(post2, account);
//        interestJpaRepository.save(interest1);
//        interestJpaRepository.save(interest2);
//
//        em.flush();
//        em.clear();
//
//        Interest interestBy1 = interestJpaRepository.findByPostId(post1.getId()).orElseThrow();
//        Interest interestBy2 = interestJpaRepository.findByPostId(post2.getId()).orElseThrow();
//        Interest interestBy3 = interestJpaRepository.findByPostId(post3.getId()).orElse(null);
//
//        assertThat(interestBy1.getPost().getId()).isEqualTo(post1.getId());
////        assertThrows(NoSuchElementException.class,
////                ()-> interestJpaRepository.findByPostId(post3.getId()));
//        assertThat(interestBy3).isNull();
//
//        interestJpaRepository.delete(interestBy1);
//
////        assertThrows(NoSuchElementException.class,
////                ()-> interestJpaRepository.findByPostId(post1.getId()));
////        Interest interestDel = interestJpaRepository.findByPostId(post1.getId()).orElseThrow();
////        assertThat(interestDel).isNull();
    }
}