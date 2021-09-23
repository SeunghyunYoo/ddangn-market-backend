package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.CreateChatRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.CreateChatResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.GetAllChatResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatroom.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllChatResponseDto>> getAllChats(@ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        List<Chat> chats = chatService.findChats(account);


        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetAllChatResponseDto(chats, account)), HttpStatus.OK);
    }


    @GetMapping("/sales")
    public ResponseEntity<ResponseOKDto<GetAllChatResponseDto>> getAllChatsByPost(
            @RequestParam Long postId, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        List<Chat> chats = chatService.findChatsByPostId(account, postId);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetAllChatResponseDto(chats, account)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseOKDto<CreateChatResponseDto>> createChat(
            @RequestBody CreateChatRequestDto createChatRequestDto, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        Long chatId = chatService.createChat(account, createChatRequestDto.getPostId());

        return new ResponseEntity<>(new ResponseOKDto<>(
                new CreateChatResponseDto(chatId)), HttpStatus.OK);
    }

    @DeleteMapping("/{chatId}")
    public ResponseEntity<ResponseSimpleOKDto> deleteChat(@PathVariable Long chatId, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);

        //TODO 실제 채팅방 구현 후 고민
        chatService.deleteChat(account, chatId);

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

}
