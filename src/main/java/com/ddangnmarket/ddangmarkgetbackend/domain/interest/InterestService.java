package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestJpaRepository interestJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public void addInterest(Long accountId, Long postId){
        Account account = accountJpaRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));

        Post post = postJpaRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));

        Interest interest = new Interest(post, account);
        interestJpaRepository.save(interest);
    }

}
