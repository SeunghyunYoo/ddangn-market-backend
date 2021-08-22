package com.ddangnmarket.ddangmarkgetbackend.api;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.dto.SignUpRequest;
import com.ddangnmarket.ddangmarkgetbackend.dto.SignUpResponse;
import com.ddangnmarket.ddangmarkgetbackend.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;
    private final AccountService accountService;

    @PostMapping("/new")
    public SignUpResponse signUp(@RequestBody SignUpRequest signUpRequest){
        Account account = modelMapper.map(signUpRequest, Account.class);
        Account result = accountService.signUp(account);
        return modelMapper.map(result, SignUpResponse.class);
    }

    @GetMapping("/info")
    public SignUpResponse info(@RequestParam String mail){
        Account account = accountService.info(mail);
        return modelMapper.map(account, SignUpResponse.class);
    }
}
