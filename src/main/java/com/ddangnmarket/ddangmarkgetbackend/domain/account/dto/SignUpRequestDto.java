package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.validation.Phone;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {
    @NotEmpty(message = "닉네임은 필수값입니다.")
    @Length(min=5, max=20, message = "5자 이상 20자 이하의 문자열이여야 합니다.")
    private String nickname;

    @NotEmpty(message = "핸드폰 번호는 필수값입니다.")
    @Phone
    private String phone;

    @NotEmpty(message = "메일은 필수 값 입니다.")
    @Email(message = "이메일 형식이 아닙니다.")
    private String mail;

    @NotEmpty(message = "비밀번호는 필수 값입니다.")
    @Length(min=8, max=20, message = "8자 이상 20자 이하의 문자열이여야 합니다.")
    private String password;
}
