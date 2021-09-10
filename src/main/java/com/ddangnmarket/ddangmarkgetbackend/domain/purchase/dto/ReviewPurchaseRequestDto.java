package com.ddangnmarket.ddangmarkgetbackend.domain.purchase.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class ReviewPurchaseRequestDto {
    @Range(min=1, max=3)
    private int score;

    private String review;

    public ReviewPurchaseRequestDto(int score, String review){
        this.score = score;
        this.review = review;
    }
}
