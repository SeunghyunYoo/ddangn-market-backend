package com.ddangnmarket.ddangmarkgetbackend.service;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.repository.InterestJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.post.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class InterestService {

    private final InterestJpaRepository interestJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public Interest enrollInterest(Long postId, Long accountId){
        Post post = postJpaRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글 입니다."));

        Account account = accountJpaRepository.findById(accountId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 회원 입니다."));

        return interestJpaRepository.save(Interest.enrollInterest(post, account));
    }

    public void removeInterest(Long postId){
        Interest interest = interestJpaRepository.findByPostId(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        interestJpaRepository.delete(interest);
    }
}
