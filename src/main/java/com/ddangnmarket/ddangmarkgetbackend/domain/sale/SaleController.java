package com.ddangnmarket.ddangmarkgetbackend.domain.sale;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.dto.ReviewSaleRequestDto;
import com.ddangnmarket.ddangmarkgetbackend.login.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/v1/sales")
@RequiredArgsConstructor
public class SaleController {

    private final SaleService saleService;
    private final AccountService accountService;

    @PostMapping("/{saleId}/reviews")
    public ResponseEntity<ResponseSimpleOKDto> review(
            @PathVariable Long saleId, @RequestBody ReviewSaleRequestDto requestDto,
            @ApiIgnore HttpSession session){

        Account sessionCheckedAccount = getSessionCheckedAccount(session);

        saleService.review(saleId, requestDto.getScore(), requestDto.getReview());

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

    private Account getSessionCheckedAccount(HttpSession session) {
        Long accountId = (Long) session.getAttribute(SessionConst.LOGIN_ACCOUNT);
        return accountService.checkSessionAndFindAccount(accountId);
    }
}
