package com.ddangnmarket.ddangmarkgetbackend.account.dto;

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
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String phone;
    @NotEmpty
    @Email
    private String mail;
    @NotEmpty
    @Length(min=8, max=20)
    private String password;
}
