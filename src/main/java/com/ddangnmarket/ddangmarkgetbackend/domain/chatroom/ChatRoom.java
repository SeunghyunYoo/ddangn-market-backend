package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author SeunghyunYoo
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatRoom implements Serializable {
    private static final long serialVersionUID = 6223_37203_68547_75807L;
    private static final int TWO = 2;

    @Id @GeneratedValue
    @Column(name = "chat_room_id")
    private Long id;

    private String roomId;

    private int accountCount;

    @OneToMany(mappedBy = "chatRoom", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<EnterInfo> enterInfos = new ArrayList<>();

    private LocalDateTime createdAt;

    public static ChatRoom creteChatRoom(){
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.accountCount = TWO;
        chatRoom.createdAt = LocalDateTime.now();
        return chatRoom;
    }

    public void addEnterInfo(EnterInfo enterInfo){
        enterInfos.add(enterInfo);
        enterInfo.setChatRoom(this);
    }

    public void plusAccountCount(){
        accountCount++;
    }

    public void minusAccountCount(){
        accountCount--;
    }

}
