package com.ddangnmarket.ddangmarkgetbackend.account;


import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.dto.AccountUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AccountService {

    private final AccountJpaRepository accountJpaRepository;
    private final AccountRepository accountRepository;

    public Account signUp(Account account){
        validateDuplicateEmailAccount(account);
        account.setCreatedAt(LocalDateTime.now());

        return accountJpaRepository.save(account);
    }

    public void delete(String mail, String password) {
        Account account = accountJpaRepository.findByMail(mail)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        accountJpaRepository.delete(account);
    }

    private void validateDuplicateEmailAccount(Account account) {
        if(accountJpaRepository.findByMail(account.getMail()).isPresent()){
            throw new IllegalStateException("이 이메일을 사용할 수 없습니다.");
        }
    }

    public void updateInfo(Account account1, AccountUpdateDto accountUpdateDto) {

    }
}
