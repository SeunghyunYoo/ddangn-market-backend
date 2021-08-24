package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SignUpResponseDto {
    private String nickname;

    private String phone;

    private String mail;
}
