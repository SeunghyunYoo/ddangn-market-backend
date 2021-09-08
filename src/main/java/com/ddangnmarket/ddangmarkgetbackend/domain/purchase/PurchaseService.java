package com.ddangnmarket.ddangmarkgetbackend.domain.purchase;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseService {

    private final PurchaseRepository purchaseRepository;

    public List<Purchase> findAllPurchase(Account account){
        return purchaseRepository.findAllByAccount(account);
    }
}
