package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {

    private final PostJpaRepository postJpaRepository;
    private final CategoryJpaRepository categoryJpaRepository;

    public Long post(Post post){
        return postJpaRepository.save(post).getId();
    }

    public Long post(String title, String desc, int price, CategoryTag categoryTag, Account account) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

        Post post = Post.createPost(title, desc, price, new PostCategory(category), account);
        return postJpaRepository.save(post).getId();
    }

    public List<Post> findAll(){
        return postJpaRepository.findAll();
    }

    public List<Post> findAllByStatus(PostStatus postStatus){
        return postJpaRepository.findAllByStatus(postStatus);
    }

    public List<Post> findAllByStatuses(List<PostStatus> postStatuses){
        return postJpaRepository.findAllByStatuses(postStatuses);
    }

    public List<Post> findPostAllBySeller(Account account){
        return postJpaRepository.findAllBySeller(account);
    };

    public List<Post> findPostAllBySellerAndStatus(Account account, PostStatus postStatus){
        return postJpaRepository.findAllBySellerAndStatus(account, postStatus);
    }

    public List<Post> findPostAllByCategory(CategoryTag categoryTag){
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);
        return postJpaRepository.findAllByCategory(category);
    }

    public List<Post> findAllByCategoryAndStatus(CategoryTag categoryTag, PostStatus postStatus){
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);
        return postJpaRepository.findAllByCategoryAndStatus(category, postStatus);
    }

    public Post findById(Long postId){
        return postJpaRepository.findById(postId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

    public void deleteById(Long postId){
        Post post = findById(postId);
        postJpaRepository.delete(post);
    }

}
