package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
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
    private Dong dong;

    @Range(min = 0, max = 3)
    private Integer range;
}
