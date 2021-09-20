package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findById(Long id);

    Optional<Chat> findByAccountAndPost(Account account, Post post);

    List<Chat> findAllByPostId(Long postId);

    @Query("select c from Chat c" +
            " join fetch c.account a" +
            " join fetch a.activityArea aa" +
            " join fetch aa.district" +
            " join fetch c.post p" +
            " join fetch c.chatRoom cr" +
            " join fetch p.seller s" +
            " join fetch s.activityArea sa" +
            " join fetch sa.district" +
            " join fetch p.district d" +
            " where p.id in :postIds and c.isDeleted = false")
    List<Chat> findAllByPostIds(@Param("postIds") Collection<Long> postIds);

    @Query("select c from Chat c" +
            " join fetch c.account a" +
            " join fetch a.activityArea aa" +
            " join fetch aa.district" +
            " join fetch c.post p" +
            " join fetch c.chatRoom cr" +
            " join fetch p.seller s" +
            " join fetch s.activityArea sa" +
            " join fetch sa.district" +
            " join fetch p.district d" +
            " where c.account = :account" +
            " and p.seller <> :account and c.isDeleted = false")
    List<Chat> findAllByAccount(@Param("account") Account account);
}
