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

    public void deleteReply(Account account, Long replyId, Long postId){
        Reply reply = replyRepository.findByIdAndPostId(replyId, postId).orElseThrow();

        if(!reply.getAccount().equals(account)){
            throw new IllegalArgumentException("해당 사용자의 댓글이 아닙니다.");
        }
        reply.deleteReply();
    }
}
