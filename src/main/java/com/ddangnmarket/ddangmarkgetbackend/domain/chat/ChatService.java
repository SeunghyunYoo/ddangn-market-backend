package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatJpaRepository chatJpaRepository;
    private final PostJpaRepository postJpaRepository;
    private final AccountJpaRepository accountJpaRepository;

    /**
     * 판매자가 올린 게시글에 대한 구매 채팅 리스트
     * @param postId
     * @return
     */
    public List<Chat> findAllByPost(Account seller, Long postId){

        Post post = getPost(postId);

        if(!post.getSeller().getId().equals(seller.getId())){
            throw new IllegalArgumentException("해당 판매자의 게시글이 아닙니다.");
        }
        return chatJpaRepository.findAllByPost(postId);
    }

    public Long createChat(Account buyer, Long postId){
        Post post = getPost(postId);
        if(post.getSeller().getId().equals(buyer.getId())){
            throw new IllegalArgumentException("자신의 게시글에는 채팅방을 만들 수 없습니다.");
        }

        Chat chat = Chat.createChat(post, buyer);
        return chatJpaRepository.save(chat).getId();
    }

    public Post getPost(Long postId){
        return postJpaRepository.findByIdWithSeller(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }

}
