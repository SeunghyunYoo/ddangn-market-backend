package com.ddangnmarket.ddangmarkgetbackend.domain.sale;

import com.ddangnmarket.ddangmarkgetbackend.api.dto.ResponseSimpleOKDto;
import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.dto.ReviewSaleRequestDto;
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

        accountService.checkSessionAndFindAccount(session);

        saleService.review(saleId, requestDto.getScore(), requestDto.getReview());

        return new ResponseEntity<>(new ResponseSimpleOKDto(), HttpStatus.OK);
    }

}
