package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.ChatJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.UpdatePostRequestDto;
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
    private final ChatJpaRepository chatJpaRepository;

    public Long post(Post post){
        return postJpaRepository.save(post).getId();
    }

    public Long post(String title, String desc, int price, CategoryTag categoryTag, Account account) {
        Category category = categoryJpaRepository.findByCategoryTag(categoryTag);

//        Post post = Post.createPost(title, desc, price, new PostCategory(category), account);
        Post post = Post.createPost(title, desc, price, category, account);
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
    }

    public List<Post> findPostAllBySellerAndStatus(Account account, PostStatus postStatus){
        return postJpaRepository.findAllBySellerAndStatus(account, postStatus);
    }

    public List<Post> findPostAllBySellerAndStatuses(Account account, List<PostStatus> postStatuses){
        return postJpaRepository.findAllBySellerAndStatuses(account, postStatuses);
    }

    public List<Post> findPostAllByCategory(CategoryTag categoryTag){
        return postJpaRepository.findAllByCategory(categoryTag);
    }

    public List<Post> findAllByCategoryAndStatus(CategoryTag categoryTag, PostStatus postStatus){
        return postJpaRepository.findAllByCategoryAndStatus(categoryTag, postStatus);
    }

    public List<Post> findAllByCategoryAndStatuses(CategoryTag categoryTag, List<PostStatus> postStatuses) {
        return postJpaRepository.findAllByCategoryAndStatuses(categoryTag, postStatuses);
    }

    public void deleteById(Long postId){
        Post post = findById(postId);
        postJpaRepository.delete(post);
    }

    public void updatePost(Long postId, UpdatePostRequestDto updatePostRequestDto){
        Post post = findById(postId);
        Category category = categoryJpaRepository.findByCategoryTag(
                updatePostRequestDto.getCategoryTag());

        post.updatePost(
                updatePostRequestDto.getTitle(),
                updatePostRequestDto.getDesc(),
                updatePostRequestDto.getPrice(),
                category
        );
    }

    public void changeReserve(Long postId, Long chatId) {
        Post post = findById(postId);

        Chat chat = chatJpaRepository.findById(chatId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅입니다."));

        post.changeReserve(chat);
    }

    public void cancelReserve(Long postId){
        Post post = findById(postId);

        post.cancelReserve();
    }

    public Post findById(Long postId){
        return postJpaRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

}
