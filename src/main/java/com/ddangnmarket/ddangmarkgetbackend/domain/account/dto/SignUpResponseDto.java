package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponseDto {
    private String nickname;

    private String phone;

    private String mail;
}
