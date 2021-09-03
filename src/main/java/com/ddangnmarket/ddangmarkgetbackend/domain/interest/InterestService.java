package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestJpaRepository interestJpaRepository;
    private final AccountJpaRepository accountJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public Long addInterest(Account account, Long postId){

        Post post = postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Interest interest = Interest.addInterest(post, account);
        return interestJpaRepository.save(interest).getId();
    }

    public List<Interest> findAllInterests(Account account){
        return interestJpaRepository.findAllByAccount(account);
    }

}
