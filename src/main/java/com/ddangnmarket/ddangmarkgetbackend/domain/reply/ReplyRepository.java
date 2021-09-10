package com.ddangnmarket.ddangmarkgetbackend.domain.reply;

import com.ddangnmarket.ddangmarkgetbackend.domain.Reply;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @EntityGraph(attributePaths = {"account"})
    List<Reply> findByPostId(Long postId);
}
