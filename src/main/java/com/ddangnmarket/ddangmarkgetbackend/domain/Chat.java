package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends DeleteEntity{

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @Enumerated(EnumType.STRING)
    private ChatStatus chatStatus;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    public static Chat createChat(Post post, Account account){
        if (post.getPostStatus() == PostStatus.COMPLETE){
            throw new IllegalArgumentException("이미 판매완료된 상품은 구매할 수 없습니다.");
        }
        validateHasAlreadyChat(post, account);
        ChatRoom chatRoom = ChatRoom.creteChatRoom();
        Chat chat = new Chat();
        chat.post = post;
        chat.account = account;
        chat.chatStatus = ChatStatus.NONE;
        chat.chatRoom = chatRoom;
        post.addChat(chat);
        return chat;
    }

    private static void validateHasAlreadyChat(Post post, Account account) {
        //TODO 원래 챗의 delete 플래그를 none으로 바꾸고 해당 챗 리턴
        boolean hasAlreadyChat = post.getChats().stream()
                .anyMatch(chat -> chat.getAccount().equals(account));
        if (hasAlreadyChat){
            throw new IllegalArgumentException("이미 채팅방이 존재합니다.");
        }
    }

    public void changeReserve(){
        chatStatus = ChatStatus.RESERVE;
    }

    public void changeNone(){
        chatStatus = ChatStatus.NONE;
    }

    public void changeComplete(){
        chatStatus = ChatStatus.COMPLETE;
    }
//
//    public void disconnectPost(){
////        this.post = null;
//        this.postStatus = PostStatus.DELETE;
//    }

    public void deleteChat(){
        super.delete();
    }
}
