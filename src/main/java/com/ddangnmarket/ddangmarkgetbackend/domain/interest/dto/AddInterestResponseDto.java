package com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddInterestResponseDto {
    private Long id;

    public AddInterestResponseDto(Long id){
        this.id = id;
    }
}
