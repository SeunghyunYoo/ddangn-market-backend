package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat extends BaseEntity{

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

    public static Chat createChat(Post post, Account account){
        if (post.getPostStatus() == PostStatus.COMPLETE){
            throw new IllegalArgumentException("이미 판매완료된 상품은 구매할 수 없습니다.");
        }
        validateHasAlreadyChat(post, account);

        Chat chat = new Chat();
        chat.post = post;
        chat.account = account;
        chat.chatStatus = ChatStatus.NONE;
        post.addChat(chat);
        return chat;
    }

    private static void validateHasAlreadyChat(Post post, Account account) {
        boolean hasAlreadyChat = post.getChats().stream()
                .anyMatch(chat -> chat.getAccount().equals(account));
        if (hasAlreadyChat){
            throw new IllegalArgumentException("이미 채팅방이 존재합니다.");
        }
    }

    public void changeReserve(){
        chatStatus = ChatStatus.RESERVED;
    }

    public void cancelReserve(){
        chatStatus = ChatStatus.NONE;
    }
}
