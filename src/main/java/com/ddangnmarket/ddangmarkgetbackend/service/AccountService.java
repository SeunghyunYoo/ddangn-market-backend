package com.ddangnmarket.ddangmarkgetbackend.service;


import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.dto.AccountUpdateDto;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountService {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public Account signUp(Account account) {
        boolean isExist = accountJpaRepository.existsByMail(account.getMail());
        if(isExist){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
        return accountJpaRepository.save(account);
    }

    public Account info(String mail){
        Account account = accountJpaRepository.findByMail(mail).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 회원 입니다."));
        return account;
    }

    public void updateInfo(Account account, AccountUpdateDto accountUpdateDto){
        modelMapper.map(accountUpdateDto, account);
        accountJpaRepository.save(account);
    }

}
