package com.ddangnmarket.ddangmarkgetbackend.domain.sale;

import com.ddangnmarket.ddangmarkgetbackend.domain.Sale;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SaleService {

    private final SaleRepository saleRepository;

    public void review(Long saleId, int score, String review){

        Sale sale = saleRepository.findById(saleId).orElseThrow();
        sale.review(score, review);

    }
}
