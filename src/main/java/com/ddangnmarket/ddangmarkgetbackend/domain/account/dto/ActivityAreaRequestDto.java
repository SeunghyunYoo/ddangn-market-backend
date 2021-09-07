package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityAreaRequestDto {
    @NotNull
    private String dong;

    @Range(min = 0, max = 3)
    private Integer range;
}
