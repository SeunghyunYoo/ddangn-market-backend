package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.ddangnmarket.ddangmarkgetbackend.domain.QAccount.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QCategory.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QDistrict.*;
import static com.ddangnmarket.ddangmarkgetbackend.domain.QPost.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SeunghyunYoo
 */
@SpringBootTest
@Transactional
class PostQuerydslTest {

    @Autowired
    EntityManager em;
    @Autowired
    JPAQueryFactory queryFactory;
//    JPAQueryFactory queryFactory = new JPAQueryFactory(em);

    @Test
    void fetchTest(){
        List<Post> result = queryFactory
                .selectFrom(post)
                .join(post.category, category).fetchJoin()
                .join(post.district, district).fetchJoin()
                .join(post.seller, account).fetchJoin()
                .where(categoryEq(CategoryTag.DIGITAL))
                .fetch();

        List<Chat> chats = result.get(0).getChats();

        for (Chat chat : chats) {
            ChatRoom chatRoom = chat.getChatRoom();
            System.out.println("chatRoom = " + chatRoom);
        }

    }

    @Test
    void fetchPagingTest(){
        List<Post> result = queryFactory
                .selectFrom(post)
                .join(post.category, category).fetchJoin()
                .join(post.district, district).fetchJoin()
                .join(post.seller, account).fetchJoin()
                .where(categoryEq(CategoryTag.DIGITAL))
                .fetch();

        List<Chat> chats = result.get(0).getChats();

        for (Chat chat : chats) {
            ChatRoom chatRoom = chat.getChatRoom();
            System.out.println("chatRoom = " + chatRoom);
        }

    }

    private BooleanExpression categoryEq(CategoryTag categoryTag){
        return category.categoryTag.eq(categoryTag);
    }
}