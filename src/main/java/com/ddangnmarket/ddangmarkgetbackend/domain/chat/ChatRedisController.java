package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.config.redis.TopicConst;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessage;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.ChatMessageService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.ChatMessagePubDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.ChatMessageSubDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.MessageType;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomRedisRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoomService;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Map;

/**
 * @author SeunghyunYoo
 */
@Controller
@RequiredArgsConstructor
public class ChatRedisController {

    private final ChatMessageService chatMessageService;
    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, ChannelTopic> topics;
    private final AccountRepository accountRepository;
    private final ChatRoomRedisRepository chatRoomRedisRepository;
    private final ChatRoomService chatRoomService;
    private final ChatRedisService chatRedisService;

    @MessageMapping("/chat/message")
    public void message(ChatMessagePubDto chatMessagePubDto,
                        @Header("simpSessionAttributes") Map<String, Object> sessionAttributes){
        Long accountId = (Long) sessionAttributes.get(SessionConst.LOGIN_ACCOUNT);
        Account account = accountRepository.findById(accountId).orElseThrow();

        String sender = account.getNickname();
        String roomId = chatMessagePubDto.getRoomId();

        ChatMessage chatMessage = new ChatMessage(
                chatMessagePubDto.getMessageType(),
                account.getNickname(),
                chatMessagePubDto.getMessage(),
                chatMessagePubDto.getRoomId());

        checkExistChatRoom(roomId);

        // TODO 어디서 처리하는게 좋은지 고민 필요
        chatMessageService.unreadToRead(roomId, sender);
        chatRedisService.sendMessage(chatMessage);
    }

    private void checkExistChatRoom(String roomId) {
        chatRoomService.findChatRoom(roomId);
    }

}
