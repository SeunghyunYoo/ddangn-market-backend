package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ActivityArea;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.dto.*;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.DistrictService;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final DistrictService districtService;

    @PostMapping("/new")
    public ResponseEntity<ResponseOKDto<SignUpResponseDto>> signUp(
            @Validated @RequestBody SignUpRequestDto signUpRequestDto,
            HttpServletRequest request, HttpServletResponse response){

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

        return new ResponseEntity<>(new ResponseOKDto<>(signUpResponseDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<ResponseOKDto<DeleteAccountResponseDto>> deleteAccount(
            @RequestBody DeleteAccountRequestDto deleteAccountRequestDto,
            HttpServletRequest request, HttpServletResponse response){

        accountService.delete(deleteAccountRequestDto.getMail(), deleteAccountRequestDto.getPassword());

        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }

        return new ResponseEntity<>(new ResponseOKDto<>(new DeleteAccountResponseDto("삭제 되었습니다.")), HttpStatus.OK);
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
    public ResponseEntity<ResponseOKDto<UpdateAccountInfoResponseDto>> updateAccountInfo(
            @Validated @RequestBody UpdateAccountInfoRequestDto updateAccountInfoRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.updateAccountInfo(account, updateAccountInfoRequestDto);
        return new ResponseEntity<>(new ResponseOKDto<>(new UpdateAccountInfoResponseDto("수정되었습니다.")), HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<ResponseOKDto<ChangeAccountPasswordResponseDto>> changePassword(
            @Validated @RequestBody ChangeAccountPasswordRequestDto changeAccountPasswordRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);
        accountService.changePassword(account, changeAccountPasswordRequestDto);
        return new ResponseEntity<>(new ResponseOKDto<>(new ChangeAccountPasswordResponseDto("수정되었습니다.")), HttpStatus.OK);
    }

    @PostMapping("/activity-area")
    public ResponseEntity<ResponseOKDto<String>> addActivityArea(
            @Validated @RequestBody ActivityAreaRequestDto activityAreaRequestDto,
            @ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccountWithArea(session);

        accountService.changeActivityArea(account,
                Dong.fromString(activityAreaRequestDto.getDong()),
                activityAreaRequestDto.getRange());

        return new ResponseEntity<>(new ResponseOKDto<>("SUCCESS"), HttpStatus.OK);
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
