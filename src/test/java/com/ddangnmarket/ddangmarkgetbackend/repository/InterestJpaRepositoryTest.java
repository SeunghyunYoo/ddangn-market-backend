package com.ddangnmarket.ddangmarkgetbackend.repository;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.post.PostJpaRepository;
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

    @Autowired InterestJpaRepository interestJpaRepository;
    @Autowired
    AccountJpaRepository accountJpaRepository;
    @Autowired
    PostJpaRepository postJpaRepository;
    @Autowired
    EntityManager em;

    @Test
    void 관심상품등록및삭제(){
        Account seller = new Account("seller");
        Account account = new Account("account");
        em.persist(seller);
        em.persist(account);


        Post post1 = Post.post("맥북", seller, DIGITAL);
        Post post2 = Post.post("그램", seller, DIGITAL);
        Post post3 = Post.post("XPS", seller, DIGITAL);

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);


        Interest interest = Interest.enrollInterest(post1, account);
        interestJpaRepository.save(interest);

        List<Interest> interests = interestJpaRepository.findAllByAccount(account);

        assertThat(interests.size()).isEqualTo(1);

        interestJpaRepository.delete(interest);
        List<Interest> interestsDelete = interestJpaRepository.findAllByAccount(account);
        assertThat(interestsDelete.size()).isEqualTo(0);

    }

    @Test
    void 게시글아이디로조회및삭제(){
        Account seller = new Account("seller");
        Account account = new Account("account");
        em.persist(seller);
        em.persist(account);


        Post post1 = Post.post("맥북", seller, DIGITAL);
        Post post2 = Post.post("그램", seller, DIGITAL);
        Post post3 = Post.post("XPS", seller, DIGITAL);

        em.persist(post1);
        em.persist(post2);
        em.persist(post3);


        Interest interest1 = Interest.enrollInterest(post1, account);
        Interest interest2 = Interest.enrollInterest(post2, account);
        interestJpaRepository.save(interest1);
        interestJpaRepository.save(interest2);

        em.flush();
        em.clear();

        Interest interestBy1 = interestJpaRepository.findByPostId(post1.getId()).orElseThrow();
        Interest interestBy2 = interestJpaRepository.findByPostId(post2.getId()).orElseThrow();
        Interest interestBy3 = interestJpaRepository.findByPostId(post3.getId()).orElse(null);

        assertThat(interestBy1.getPost().getId()).isEqualTo(post1.getId());
//        assertThrows(NoSuchElementException.class,
//                ()-> interestJpaRepository.findByPostId(post3.getId()));
        assertThat(interestBy3).isNull();

        interestJpaRepository.delete(interestBy1);

//        assertThrows(NoSuchElementException.class,
//                ()-> interestJpaRepository.findByPostId(post1.getId()));
//        Interest interestDel = interestJpaRepository.findByPostId(post1.getId()).orElseThrow();
//        assertThat(interestDel).isNull();
    }
}