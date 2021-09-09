package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<Chat, Long> {

    Optional<Chat> findById(Long id);


    Optional<Chat> findByAccountAndPostId(Account account, Long postId);

    List<Chat> findAllByPostId(Long postId);

    @Query("select c from Chat c" +
            " join fetch c.account a" +
            " join fetch c.post p" +
            " where p.id in :postIds and c.isDeleted = false")
    List<Chat> findAllByPostIds(@Param("postIds") Collection<Long> postIds);

    @Query("select c from Chat c" +
            " join fetch c.post p" +
            " where c.account = :account" +
            " and p.seller <> :account and c.isDeleted = false")
    List<Chat> findAllByAccount(@Param("account") Account account);
}
