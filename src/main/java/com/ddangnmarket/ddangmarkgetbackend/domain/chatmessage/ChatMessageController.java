package com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.chatmessage.dto.GetMessageResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author SeunghyunYoo
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chats/messages")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;
    private final AccountService accountService;

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetMessageResponseDto>> getMessages(
            @RequestParam String roomId, @ApiIgnore HttpSession session){
        Account account = accountService.checkSessionAndFindAccount(session);
        List<ChatMessage> chatMessages = chatMessageService.findAllMessage(account.getNickname(), roomId);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetMessageResponseDto(chatMessages)), HttpStatus.OK);
    }
}
