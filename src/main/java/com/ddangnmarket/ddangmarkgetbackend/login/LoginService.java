package com.ddangnmarket.ddangmarkgetbackend.login;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountRepository accountRepository;

    public Account login(String mail, String password){
        Account account = accountRepository.findByMail(mail)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        if(!account.getPassword().equals(password)){
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        return account;
    }

}
