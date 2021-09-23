package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author SeunghyunYoo
 */

public interface EnterInfoRepository extends JpaRepository<EnterInfo, Long> {

    @Query("select ei from EnterInfo ei" +
            " join fetch ei.account a" +
            " join fetch ei.chatRoom cr" +
            " where cr.roomId = :roomId")
    List<EnterInfo> findByRoomId(@Param("roomId") String roomId);
}
