package com.ddangnmarket.ddangmarkgetbackend.account;

import com.ddangnmarket.ddangmarkgetbackend.account.dto.DeleteAccountRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.account.dto.DeleteAccountResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.account.dto.SignUpRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.account.dto.SignUpResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

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

    @PostMapping("/delete")
    public ResponseEntity<DeleteAccountResponseDto> deleteAccount(
            @RequestBody DeleteAccountRequestDto deleteAccountRequestDto,
            HttpServletRequest request, HttpServletResponse response){

        accountService.delete(deleteAccountRequestDto.getMail(), deleteAccountRequestDto.getPassword());
        return new ResponseEntity<>(new DeleteAccountResponseDto("삭제 되었습니다."), HttpStatus.OK);
    }

}
