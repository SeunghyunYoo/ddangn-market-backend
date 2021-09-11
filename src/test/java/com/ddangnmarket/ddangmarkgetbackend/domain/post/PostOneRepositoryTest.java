package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Reply;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostOneRepositoryTest {

    @Autowired PostOneRepository postOneRepository;
    @Autowired PostRepository repository;
    @Test
    void postOneTest(){
        Post post = postOneRepository.getPostOneInfo(34L);

        Dong dong = post.getDistrict().getDong();

        List<Reply> replies = post.getReplies();

        for (Reply reply : replies) {
            System.out.println("reply = " + reply.getContent());
            System.out.println("reply = " + reply.getAccount().getNickname());
            System.out.println("reply = " + reply.getAccount().getCreatedAt());
        }

        System.out.println("---");
    }
}