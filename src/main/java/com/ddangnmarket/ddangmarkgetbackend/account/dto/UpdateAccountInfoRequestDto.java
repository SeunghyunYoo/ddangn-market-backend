package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.account.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAccountInfoRequestDto {
    @Length(min=5, max=20, message = "5자 이상 20자 이하의 문자열이여야 합니다.")
    private String nickname;

    @Phone
    private String phone;
}
