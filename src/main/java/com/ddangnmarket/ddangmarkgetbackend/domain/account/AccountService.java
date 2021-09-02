package com.ddangnmarket.ddangmarkgetbackend.domain.account;


import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.UpdateAccountInfoRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.ChangeAccountPasswordRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateEmailException;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateNicknameException;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
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
        validateDuplicateNicknameAccount(account.getNickname());

        account.setCreatedAt(LocalDateTime.now());

        return accountJpaRepository.save(account);
    }

    public Account findAccount(Long accountId){
        return accountJpaRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));
    }

    public void delete(String mail, String password) {
        Account account = accountJpaRepository.findByMail(mail)
                .orElseThrow(() -> new IllegalStateException("잘못된 회원정보입니다."));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 회원정보입니다.");
        }
        accountJpaRepository.delete(account);
    }

    public void updateAccountInfo(Account account, UpdateAccountInfoRequestDto updateAccountInfoRequestDto) {
        if(updateAccountInfoRequestDto.getPhone() != null){
            account.changePhone(updateAccountInfoRequestDto.getPhone());
        }
        if(updateAccountInfoRequestDto.getNickname() != null){
            validateDuplicateNicknameAccount(updateAccountInfoRequestDto.getNickname());
            account.changeNickname(updateAccountInfoRequestDto.getNickname());
        }
    }

    public void changePassword(Account account, ChangeAccountPasswordRequestDto changeAccountPasswordRequestDto){
        if(changeAccountPasswordRequestDto.getPassword().equals(account.getPassword())){
            throw new IllegalArgumentException("같은 비밀번호로 변경 불가능합니다");
        }
        account.changePassword(changeAccountPasswordRequestDto.getPassword());
    }

    private void validateDuplicateEmailAccount(Account account) {
        if(accountJpaRepository.findByMail(account.getMail()).isPresent()){
            throw new DuplicateEmailException("이 이메일을 사용할 수 없습니다.");
        }
    }

    private void validateDuplicateNicknameAccount(String nickname){
        if(accountJpaRepository.findByNickname(nickname).isPresent()){
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
        }
    }

}
