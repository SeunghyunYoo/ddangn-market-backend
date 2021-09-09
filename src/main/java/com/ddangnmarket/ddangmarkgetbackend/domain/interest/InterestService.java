package com.ddangnmarket.ddangmarkgetbackend.domain.interest;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class InterestService {

    private final InterestRepository interestRepository;
    private final PostRepository postRepository;

    public Long addInterest(Account account, Long postId){

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

        Interest interest = interestRepository.findByAccountAndPostId(account, postId).orElse(null);
        if (interest == null) {
            Interest newInterest = Interest.addInterest(post, account);
            return interestRepository.save(newInterest).getId();
        }
        interest.cancelDelete();
        return interest.getId();
    }

    public List<Interest> findAllInterests(Account account){
        return interestRepository.findAllByAccount(account);
    }

    public void deleteInterest(Account account, Long interestId){

        Interest interest = interestRepository.findByIdAndAccount(interestId, account)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 id이거나 해당 사용자의 interest가 아닙니다"));


//        interestRepository.delete(interest);
        interest.delete();
    }

    public void deleteInterestByPostId(Account account, Long postId){
        Interest interest = interestRepository
                .findByAccountAndPostId(account, postId).orElseThrow();
        interest.delete();
    }
}
