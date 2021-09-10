package com.ddangnmarket.ddangmarkgetbackend.domain.reply;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.Reply;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;
    private final PostRepository postRepository;

    public void createReply(Account account, Long postId, String content){
        Post post = postRepository.findById(postId).orElseThrow();
        Reply.createReply(post, account, content);
    }

    public void deleteReply(Long replyId){
        Reply reply = replyRepository.findById(replyId).orElseThrow();
        reply.deleteReply();
    }
}
