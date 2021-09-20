package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author SeunghyunYoo
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6223_37203_68547_75807L;
    @Id @GeneratedValue
    @Column
    private Long id;

    private String roomId;

    private int accountCount;

    private int readingCount;

    private LocalDateTime createdAt;

    public static ChatRoom creteChatRoom(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.createdAt = LocalDateTime.now();
        return chatRoom;
    }
}
