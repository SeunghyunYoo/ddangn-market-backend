package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final PostRepository postRepository;

    /**
     * 판매자가 올린 게시글에 대한 구매 채팅 리스트
     * @param postId
     * @return
     */
    public List<Chat> findChatsByPostId(Account seller, Long postId){

        Post post = findPost(postId);

//        if(!post.getSeller().getId().equals(seller.getId())){
        if(!post.getSeller().equals(seller)){
            throw new IllegalArgumentException("해당 판매자의 게시글이 아닙니다.");
        }
        return chatRepository.findAllByPostId(postId);
    }

    public List<Chat> findChats(Account account){
        List<Long> postIds = postRepository.findAllIdsBySeller(account)
                .stream().map(Post::getId).collect(Collectors.toList());

        List<Chat> allBySeller = chatRepository.findAllByPostIds(postIds);

        List<Chat> allByAccount = chatRepository.findAllByAccount(account);

        return Stream.concat(allByAccount.stream(), allBySeller.stream())
                .sorted(Comparator.comparing(Chat::getUpdatedAt).reversed())
                .collect(Collectors.toList());
    }



    public Long createChat(Account buyer, Long postId){
        Post post = findPost(postId);
        if(post.getSeller().getId().equals(buyer.getId())){
            throw new IllegalArgumentException("자신의 게시글에는 채팅방을 만들 수 없습니다.");
        }
        Chat chat = chatRepository.findByAccountAndPostId(buyer, postId).orElse(null);


        if(chat != null){
            chat.cancelDelete();
            return chat.getId();
        }
        Chat newChat = Chat.createChat(post, buyer);
        return chatRepository.save(newChat).getId();
    }

    public void deleteChat(Account account, Long chatId){
//        chatRepository.deleteById(chatId);
        chatRepository.findById(chatId).orElseThrow().deleteChat();

    }

    private Post findPost(Long postId){

        return postRepository.findWithSellerById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }


}
