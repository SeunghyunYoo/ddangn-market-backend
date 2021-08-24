package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpRequestDto {
    private String nickname;

    private String phone;

    private String mail;

    private String password;
}
