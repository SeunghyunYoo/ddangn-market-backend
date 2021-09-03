package com.ddangnmarket.ddangmarkgetbackend.domain.chat;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Chat;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.CreateChatRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.CreateChatResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.chat.dto.GetAllChatResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/api/v1/chats")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllChatResponseDto>> getAllChatByPost(
            @RequestParam Long postId, @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);

        List<Chat> chats = chatService.findAllByPost(account, postId);

        return new ResponseEntity<>(new ResponseOKDto<>(new GetAllChatResponseDto(chats)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseOKDto<CreateChatResponseDto>> createChat(
            @RequestBody CreateChatRequestDto createChatRequestDto, @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);

        Long chatId = chatService.createChat(account, createChatRequestDto.getPostId());

        return new ResponseEntity<>(new ResponseOKDto<>(new CreateChatResponseDto(chatId)), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);

        return accountService.findAccount(accountId);
    }
}
