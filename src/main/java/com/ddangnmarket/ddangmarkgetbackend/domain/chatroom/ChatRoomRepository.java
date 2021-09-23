package com.ddangnmarket.ddangmarkgetbackend.domain.chatroom;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author SeunghyunYoo
 */
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @EntityGraph(attributePaths = {"enterInfos"})
    Optional<ChatRoom> findByRoomId(String roomId);

    @Query("select cr from ChatRoom cr" +
            " join fetch cr.enterInfos ei" +
            " join fetch ei.account" +
            " where cr.roomId = :roomId")
    Optional<ChatRoom> findWithEnterInfoByRoomId(@Param("roomId") String roomId);
}
