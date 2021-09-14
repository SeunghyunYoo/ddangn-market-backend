package com.ddangnmarket.ddangmarkgetbackend.domain.account;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.purchase.PurchaseRepository;
import com.ddangnmarket.ddangmarkgetbackend.domain.sale.SaleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MannerService {

    private final AccountRepository accountRepository;
    private final PurchaseRepository purchaseRepository;
    private final SaleRepository saleRepository;
    private final double DEFAULT_MANNER_TEMP = 36.5;

    @Scheduled(fixedRate = 1000*60*10, initialDelay = 1000*10)
    public void updateManner(){
//        log.info("updateManner start {}", LocalDateTime.now());
        List<Account> accounts = accountRepository.findAll();

        accounts.forEach(account -> {
            long purchaseCount = purchaseRepository.countByAccount(account);
            Double purchaseScore = purchaseRepository.findAvgScoreByAccount(account).orElse((double) 0);

            long saleCount = saleRepository.countByAccount(account);
            double saleScore = saleRepository.findAvgScoreByAccount(account).orElse((double) 0);

            double mannerTemp = DEFAULT_MANNER_TEMP
                    + purchaseCount * (purchaseScore - 2) / 5
                    + saleCount * (saleScore - 2) / 5;

            account.setMannerTemp(mannerTemp);
        });
//        log.info("updateManner end {}", LocalDateTime.now());

    }
}
