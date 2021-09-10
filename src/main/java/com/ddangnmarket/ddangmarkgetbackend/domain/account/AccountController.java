package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictService;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.*;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final DistrictService districtService;

    @PostMapping("/new")
    public ResponseEntity<ResponseSimpleOKDto> signUp(
            @Validated @RequestBody SignUpRequestDto signUpRequestDto){

        Account account = new Account(
                signUpRequestDto.getNickname(),
                signUpRequestDto.getPhone(),
                signUpRequestDto.getMail(),
                signUpRequestDto.getPassword());

        Account result = accountService.signUp(account, signUpRequestDto.getDong());

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto(
                result.getNickname(),
                result.getPhone(),
                result.getMail());

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseSimpleOKDto> deleteAccount(
            @RequestBody DeleteAccountRequestDto deleteAccountRequestDto,
            HttpServletRequest request){

        accountService.delete(deleteAccountRequestDto.getMail(), deleteAccountRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @DeleteMapping("/absolute")
    public ResponseEntity<ResponseSimpleOKDto> absoluteDeleteAccount(
            @RequestBody DeleteAccountRequestDto deleteAccountRequestDto,
            HttpServletRequest request){

        accountService.absoluteDelete(deleteAccountRequestDto.getMail(), deleteAccountRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @GetMapping("/info")
    public ResponseEntity<ResponseOKDto<GetAccountInfoResponseDto>> getAccountInfo(@ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccountWithArea(session);
        // TODO Account2Area fetch join 고민 필요
        List<Dong> activityAreas = districtService.getActivityAreas(account);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetAccountInfoResponseDto(account, activityAreas)),HttpStatus.OK);
    }

    @PutMapping("/info")
    public ResponseEntity<ResponseSimpleOKDto> updateAccountInfo(
            @Validated @RequestBody UpdateAccountInfoRequestDto updateAccountInfoRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.updateAccountInfo(account, updateAccountInfoRequestDto);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseSimpleOKDto> changePassword(
            @Validated @RequestBody ChangeAccountPasswordRequestDto changeAccountPasswordRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.changePassword(account, changeAccountPasswordRequestDto);
        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    @PostMapping("/activity-area")
    public ResponseEntity<ResponseOKDto<ActivityAreaResponseDto>> addActivityArea(
            @Validated @RequestBody ActivityAreaRequestDto activityAreaRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccountWithArea(session);

        accountService.changeActivityArea(account,
                Dong.fromString(activityAreaRequestDto.getDong()),
                activityAreaRequestDto.getRange());

        List<Dong> activityAreas = districtService.getActivityAreas(account);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new ActivityAreaResponseDto(activityAreas, activityAreaRequestDto.getRange())), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.findAccount(accountId);
    }

    private Account getSessionCheckedAccountWithArea(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.findAccountWithActivityArea(accountId);
    }

}
