package com.ddangnmarket.ddangmarkgetbackend.service;


import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountJpaRepository;
import com.ddangnmarket.ddangmarkgetbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountRepository accountRepository;

}
