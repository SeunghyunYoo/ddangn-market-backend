package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong.GUMI;

/**
 * @author SeunghyunYoo
 */
@SpringBootTest
@Transactional
class PostQueryRepositoryTest {

    @Autowired
    PostRepositoryImpl postQueryRepository;
    @Autowired
    DistrictRepository districtRepository;

    @Test
    void pagePosts(){

        District gumi = districtRepository.findByDong(GUMI);

        PageRequest pageRequest = PageRequest.of(0, 5);
        Page<Post> pagePosts = postQueryRepository.getPagePosts(List.of(gumi), pageRequest);

        List<Post> content = pagePosts.getContent();

        for (Post post : content) {
            System.out.println("post = " + post.getTitle());
        }
    }

    @Test
    void pagePostsByPostStatus(){
        District gumi = districtRepository.findByDong(GUMI);

        PageRequest pageRequest = PageRequest.of(0, 5);
//        pageRequest.withSort(Sort.by(Sort.Direction.ASC, post.createdAt.toString()));

        Page<Post> pagePosts = postQueryRepository.getPagePostByStatus(List.of(gumi), List.of(), pageRequest);

        List<Post> content = pagePosts.getContent();

        for (Post post : content) {
            System.out.println("post = " + post.getTitle());
            System.out.println("post = " + post.getCreatedAt());
        }
    }
}