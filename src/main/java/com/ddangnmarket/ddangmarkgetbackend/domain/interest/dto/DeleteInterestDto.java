package com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeleteInterestDto {
    private Long id;

    public DeleteInterestDto(Long id){
        this.id = id;
    }
}
