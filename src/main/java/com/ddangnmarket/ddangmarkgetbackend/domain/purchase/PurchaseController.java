package com.ddangnmarket.ddangmarkgetbackend.domain.purchase;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.dto.GetAllPurchaseResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.dto.GetPurchaseResponseDto;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("api/v1/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final AccountService accountService;
    private final PurchaseService purchaseService;

    @GetMapping
    public ResponseEntity<ResponseOKDto<GetAllPurchaseResponseDto>> getAllPurchase(@ApiIgnore HttpSession session){
        Account account = getSessionCheckedAccount(session);

        List<Purchase> purchases = purchaseService.findAllPurchase(account);

        return new ResponseEntity<>(new ResponseOKDto<>(
                new GetAllPurchaseResponseDto(purchases)), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.findAccount(accountId);
    }
}
