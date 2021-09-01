package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangeAccountPasswordRequestDto {
    @NotEmpty
    @Length(min=8, max=20, message = "8자 이상 20자 이하의 문자열이여야 합니다.")
    private String password;
}
