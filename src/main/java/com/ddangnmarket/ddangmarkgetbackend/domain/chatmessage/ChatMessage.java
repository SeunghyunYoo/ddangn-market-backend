package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage;

import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.MessageType;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author SeunghyunYoo
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessage {
    private final int TWO = 2;

    @Id @GeneratedValue
    @Column(name = "chat_message_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    // TODO Account entity
    private String sender;

    private int unreadCount;

    @Lob
    private String message;

    // TODO ChatRoom entity
    private String roomId;

    // 바로 redis sub로 넘겨줄때 값이 필요하므로 createdDate annotaion으로 사용 안하고
    // 생성자에서 직접 값 초기화
    private LocalDateTime createdAt;

    public ChatMessage(MessageType messageType, String sender, String message, String roomId){
        this.messageType = messageType;
        this.sender = sender;
        this.message = message;
        this.roomId = roomId;
        this.createdAt = LocalDateTime.now();
        this.unreadCount = TWO - 1;
    }

    public void decreaseUnreadCount(){
        unreadCount -= 1;
    }


}
