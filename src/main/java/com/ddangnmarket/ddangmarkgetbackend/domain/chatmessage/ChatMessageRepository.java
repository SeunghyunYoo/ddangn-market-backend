package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage;

import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author SeunghyunYoo
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findAllByRoomIdOrderByCreatedAtAsc(String roomId);

    @Query("select m from ChatMessage m" +
            " where m.roomId = :roomId" +
            " and m.unreadCount <> 0" +
            " and m.sender <> :sender")
    List<ChatMessage> findUnreadMsgByRoomId(@Param("sender") String sender, @Param("roomId") String roomId);

    @Query("select m from ChatMessage m" +
            " where m.roomId = :roomId" +
            " and m.messageType ='ENTER'" +
            " and m.sender = :sender")
    Optional<ChatMessage> findEnterMsg(@Param("sender") String sender, @Param("roomId") String roomId);

}
