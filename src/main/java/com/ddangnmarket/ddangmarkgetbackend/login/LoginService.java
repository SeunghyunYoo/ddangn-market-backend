package com.ddangnmarket.ddangmarkgetbackend.login;

import com.ddangnmarket.ddangmarkgetbackend.account.AccountJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final AccountJpaRepository accountJpaRepository;

}
