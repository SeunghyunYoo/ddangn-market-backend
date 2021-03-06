package com.ddangnmarket.ddangmarkgetbackend.login;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountServiceV1;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.login.dto.LoginRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.login.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.ddangnmarket.ddangmarkgetbackend.login.SessionConst.LOGIN_ACCOUNT;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class LoginController {

    private final AccountService accountService;
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ResponseOKDto<String>> login(
            @RequestBody LoginRequestDto loginRequestDto, BindingResult bindingResult,
            HttpServletRequest request, HttpServletResponse response){

        Account loginAccount = loginService.login(loginRequestDto.getMail(), loginRequestDto.getPassword());

        HttpSession session = request.getSession(true);

        session.setAttribute(LOGIN_ACCOUNT, loginAccount.getId());

//        LoginResponseDto loginInfo =  (LoginResponseDto) session.getAttribute(LOGIN_ACCOUNT);
//        log.info(loginInfo.toString());
        session.getAttributeNames().asIterator().forEachRemaining(name ->
                log.info("session name={}, value={}", name, session.getAttribute(name))
        );
        log.info(session.getId());

        return new ResponseEntity<>(new ResponseOKDto<>("????????????????????????."), HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseOKDto<String>> logout(@ApiIgnore HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session != null){
            session.invalidate();
        }
        return new ResponseEntity<>(new ResponseOKDto<>("???????????? ???????????????."), HttpStatus.OK);
    }

//    @GetMapping("/sessionInfo")
    public String sessionInfo(HttpServletRequest request){
        HttpSession session = request.getSession(false);

        LoginResponseDto attribute = (LoginResponseDto) session.getAttribute(LOGIN_ACCOUNT);
        log.info(attribute.toString());
        session.getAttributeNames().asIterator().forEachRemaining(name ->
                log.info("session name={}, value={}", name, session.getAttribute(name))
        );
        log.info(session.getId());
        return "session";
    }
}
