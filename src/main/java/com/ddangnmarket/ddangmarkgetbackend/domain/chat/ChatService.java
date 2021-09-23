package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.Post;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomRedisRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.EnterInfo;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;
    private final PostRepository postRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;

    /**
     * 판매자가 올린 게시글에 대한 구매 채팅 리스트
     * @param postId
     * @return
     */
    public List<Chat> findChatsByPostId(Account seller, Long postId){

        Post post = findPost(postId);

        validateIsSellerPost(seller, post);
        return chatRepository.findAllByPostId(postId);
    }

    private void validateIsSellerPost(Account seller, Post post) {
        if(!post.getSeller().equals(seller)){
            throw new IllegalArgumentException("해당 판매자의 게시글이 아닙니다.");
        }
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
        validateIsBuyerPost(buyer, post);
        Optional<Chat> optChat = chatRepository.findByAccountAndPost(buyer, post);

        if(optChat.isPresent()){
            Chat chat = optChat.get();
            chat.cancelDelete();
            return chat.getId();
        }

        // TODO
//        ChatRoom chatRoom = ChatRoom.creteChatRoom();
//        ChatRoom saveChatRoom = chatRoomRepository.save(chatRoom);
//        chatRoomRedisRepository.save(saveChatRoom);
//        Account seller = post.getSeller();
//        ChatRoom chatRoom = ChatRoom.creteChatRoom();
//        chatRoom.addEnterInfo(EnterInfo.createEnterInfo(buyer));
//        chatRoom.addEnterInfo(EnterInfo.createEnterInfo(seller));
        Chat newChat = Chat.createChat(post, buyer);

//        ChatRoom chatRoom = newChat.getChatRoom();
//        chatRoomRedisRepository.save(chatRoom);
        return chatRepository.save(newChat).getId();
    }

    private void validateIsBuyerPost(Account buyer, Post post) {
        if(post.getSeller().equals(buyer)){
            throw new IllegalArgumentException("자신의 게시글에는 채팅방을 만들 수 없습니다.");
        }
    }

    public void deleteChat(Account account, Long chatId){
        // TODO 실제 채팅 구현 필요, 그 후 삭제 적용, buyer, seller 채팅 방 연관
//        chatRepository.deleteById(chatId);
        chatRepository.findById(chatId).orElseThrow().deleteChat();

    }

    private Post findPost(Long postId){

        return postRepository.findWithSellerById(postId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    }


}
