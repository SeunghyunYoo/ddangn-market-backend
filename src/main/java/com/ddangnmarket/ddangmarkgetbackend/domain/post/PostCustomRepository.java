package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.search.PostSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author SeunghyunYoo
 */
public interface PostCustomRepository {
    Page<Post> getPagePosts(List<District> districts, Pageable pageable);
    Page<Post> getPagePostByStatus(
            List<District> districts, List<PostStatus> postStatuses, Pageable pageable);
    Page<Post> getPagePostsByStatusAndCategory(
            List<District> districts, List<PostStatus> postStatuses, CategoryTag categoryTag, Pageable pageable);
    Page<Post> getPagePostsBySellerAndStatus(
            Account seller, List<PostStatus> postStatuses, Pageable pageable);
    Page<Post> getPagePostBySearch(
            List<District> districts, PostSearchCondition condition, Pageable pageable);
}
