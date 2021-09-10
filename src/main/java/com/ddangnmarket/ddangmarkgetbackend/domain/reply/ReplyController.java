package com.ddangnmarket.ddangmarkgetbackend.domain.reply;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.reply.dto.ReplyRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/posts")
public class ReplyController{

    private final AccountService accountService;
    private final ReplyService replyService;

    @PostMapping("{postId}/replies")
    public ResponseEntity<ResponseSimpleOKDto> reply(
            @PathVariable Long postId, @RequestBody ReplyRequestDto requestDto,
            @ApiIgnore HttpSession session){

        Account account = accountService.findAccount(session);

        replyService.createReply(account, postId, requestDto.getContent());

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }
}
