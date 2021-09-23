package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author SeunghyunYoo
 */
@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EnterInfo {

    @Id @GeneratedValue
    @Column(name = "enter_info_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    private int unreadCount;

    public static EnterInfo createEnterInfo(Account account){
        EnterInfo enterInfo = new EnterInfo();
        enterInfo.account = account;
        enterInfo.unreadCount = 0;
        return enterInfo;
    }

    public void increaseUnreadCount(){
        unreadCount++;
    }

    public void resetUnreadCount(){
        unreadCount = 0;
    }

}
