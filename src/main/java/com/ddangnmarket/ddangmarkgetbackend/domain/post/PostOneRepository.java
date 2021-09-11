package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.dto.GetPostOneResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class PostOneRepository {

    private final EntityManager em;

    public Post getPostOneInfo(Long id){
        // TODO sale, purchase oneToOne 관계인데 연관관계 주인을 바꾸던가 해야할듯
        // 어짜피 sale, purchase에서는 post를 항상 가져옴
        // sale, purchase는 이거로 그냥 가져와서 일단 페치조인 으로 처리
        Post post = em.createQuery("select p from Post p" +
                        " join fetch p.seller s" +
                        " join fetch p.district d" +
                        " join fetch p.sale sl" +
                        " join fetch p.purchase pc" +
                        " where p.id = :id", Post.class)
                .setParameter("id", id)
                .getResultStream()
                .findAny()
                .orElseThrow();

        // chats, replies 둘다 가져와야 함
        return post;
    }
}
