package com.ddangnmarket.ddangmarkgetbackend.domain.account;


import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ActivityArea;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.ChangeAccountPasswordRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.UpdateAccountInfoRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateEmailException;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.exception.DuplicateNicknameException;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final DistrictRepository districtRepository;
    private final AccountRepository accountRepository;

    public Account signUp(Account account, Dong dong){
        validateDuplicateEmailAccount(account);
        validateDuplicateNicknameAccount(account.getNickname());
        District district = districtRepository.findByDong(dong);
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소입니다."));;

        account.changeActivityArea(ActivityArea.createActiveArea(district, 0));
//        account.setCreatedAt(LocalDateTime.now());

        return accountRepository.save(account);
    }


    public Account checkSessionAndFindAccount(Long accountId){
        return accountRepository.findById(accountId).orElseThrow(() ->
                new IllegalStateException("존재하지 않는 회원입니다."));
    }

    public Account findAccountWithActivityAreas(Long accountId){
        return accountRepository.findWithActivityAreaById(accountId).orElseThrow();
    }

    public Account checkSessionAndFindAccount(HttpSession session){
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return checkSessionAndFindAccount(accountId);
    }

    public Account findAccountWithActivityArea(Long accountId){
        return accountRepository.findWithActivityAreaById(accountId)
                .orElseThrow(() -> new IllegalStateException("존재하지 않는 회원입니다."));
    }

    public void absoluteDelete(String mail, String password) {
        Account account = accountRepository.findByMail(mail)
                .orElseThrow(() -> new IllegalStateException("잘못된 회원정보입니다."));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 회원정보입니다.");
        }
//        account.absoluteDeleteAccount();
        accountRepository.delete(account);
    }

    public void delete(String mail, String password) {
        Account account = accountRepository.findByMail(mail)
                .orElseThrow(() -> new IllegalStateException("잘못된 회원정보입니다."));

        if (!account.getPassword().equals(password)) {
            throw new IllegalArgumentException("잘못된 회원정보입니다.");
        }
        account.deleteAccount();
    }

    public void updateAccountInfo(Account account, UpdateAccountInfoRequestDto updateAccountInfoRequestDto) {

        if (!account.getNickname().equals(updateAccountInfoRequestDto.getNickname())) {
            validateDuplicateNicknameAccount(updateAccountInfoRequestDto.getNickname());
        }

        account.changeAccountInfo(
                updateAccountInfoRequestDto.getNickname(),
                updateAccountInfoRequestDto.getPhone()
        );
    }

    public void changePassword(Account account, ChangeAccountPasswordRequestDto changeAccountPasswordRequestDto){
        if(changeAccountPasswordRequestDto.getPassword().equals(account.getPassword())){
            throw new IllegalArgumentException("같은 비밀번호로 변경 불가능합니다");
        }
        account.changePassword(changeAccountPasswordRequestDto.getPassword());
    }

    public void changeActivityArea(Account account, Dong dong, Integer range){
        District district = districtRepository.findByDong(dong);
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주소입니다."));;
        account.getActivityArea().changeActiveArea(district, range);
    }

    private void validateDuplicateEmailAccount(Account account) {
        if(accountRepository.findByMail(account.getMail()).isPresent()){
            throw new DuplicateEmailException("이 이메일을 사용할 수 없습니다.");
        }
    }

    private void validateDuplicateNicknameAccount(String nickname){
        if(accountRepository.findByNickname(nickname).isPresent()){
            throw new DuplicateNicknameException("이미 존재하는 닉네임입니다.");
        }
    }
//    @Scheduled(fixedRate = 1000 * 60 * 10, initialDelay = 3000)
//    public void test(){
//        log.info("Scheduling test {}", LocalDateTime.now());
//    }

}
