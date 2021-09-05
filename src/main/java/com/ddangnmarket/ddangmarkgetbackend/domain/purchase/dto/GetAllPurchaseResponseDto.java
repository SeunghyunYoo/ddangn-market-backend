package com.ddangnmarket.ddangmarkgetbackend.domain.purchase.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Purchase;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllPurchaseResponseDto {
    private List<GetPurchaseResponseDto> purchases;

    public GetAllPurchaseResponseDto(List<Purchase> purchases){
        this.purchases = purchases.stream()
                .map(GetPurchaseResponseDto::new).collect(Collectors.toList());
    }
}
