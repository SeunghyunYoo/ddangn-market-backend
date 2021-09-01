package com.ddangnmarket.ddangmarkgetbackend.account;

import com.ddangnmarket.ddangmarkgetbackend.account.dto.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountJpaRepository accountJpaRepository;

    @PostMapping("/new")
    public ResponseEntity<SignUpResponseDto> signUp(
            @Validated @RequestBody SignUpRequestDto signUpRequestDto,
            HttpServletRequest request, HttpServletResponse response){

        Account account = new Account(
                signUpRequestDto.getNickname(),
                signUpRequestDto.getPhone(),
                signUpRequestDto.getMail(),
                signUpRequestDto.getPassword());

        Account result = accountService.signUp(account);

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto(
                result.getNickname(),
                result.getPhone(),
                result.getMail());

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<DeleteAccountResponseDto> deleteAccount(
            @RequestBody DeleteAccountRequestDto deleteAccountRequestDto,
            HttpServletRequest request, HttpServletResponse response){

        accountService.delete(deleteAccountRequestDto.getMail(), deleteAccountRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(new DeleteAccountResponseDto("삭제 되었습니다."), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<GetAccountInfoResponseDto> getAccountInfo(HttpSession session){
        Account account = getSessionCheckedAccount(session);
        return new ResponseEntity<>(
                new GetAccountInfoResponseDto(account.getMail(), account.getNickname(), account.getPhone()),
                HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity<UpdateAccountInfoResponseDto> updateAccountInfo(
            @Validated @RequestBody UpdateAccountInfoRequestDto updateAccountInfoRequestDto, HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.updateAccountInfo(account, updateAccountInfoRequestDto);
        return new ResponseEntity<>(new UpdateAccountInfoResponseDto("수정되었습니다."), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<ChangeAccountPasswordResponseDto> changePassword(
            @Validated @RequestBody ChangeAccountPasswordRequestDto changeAccountPasswordRequestDto,
                               HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.changePassword(account, changeAccountPasswordRequestDto);
        return new ResponseEntity<>(new ChangeAccountPasswordResponseDto("수정되었습니다."), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);

        Account account = accountJpaRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));
        return account;
    }


}
