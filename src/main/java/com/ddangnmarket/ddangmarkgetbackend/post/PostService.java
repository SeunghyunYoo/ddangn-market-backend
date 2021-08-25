package com.ddangnmarket.ddangmarkgetbackend.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostJpaRepository postJpaRepository;

    public Long post(Post post){
        return postJpaRepository.save(post).getId();
    }

    public List<Post> findPostAllBySeller(Account account){
        return postJpaRepository.findAllBySeller(account);
    };

    public List<Post> findAll(){
        return postJpaRepository.findAllByStatus(Status.NEW);
    }

    public List<Post> findAllByCategoryTag(CategoryTag categoryTag){
        return postJpaRepository.findAllByCategoryTagAndStatus(categoryTag, Status.NEW);
    }

    public Post findById(Long postId){
        return postJpaRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public void deleteById(Long postId){
        Post post = findById(postId);
        postJpaRepository.delete(post);
    }
}
