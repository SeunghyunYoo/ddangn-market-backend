package com.ddangnmarket.ddangmarkgetbackend.domain.sale.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

@Data
@NoArgsConstructor
public class ReviewSaleRequestDto {
    @Range(min=1, max=3)
    private int score;

    private String review;

    public ReviewSaleRequestDto(int score, String review){
        this.score = score;
        this.review = review;
    }
}
