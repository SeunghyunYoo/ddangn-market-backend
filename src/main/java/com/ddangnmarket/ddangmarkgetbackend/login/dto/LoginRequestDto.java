package com.ddangnmarket.ddangmarkgetbackend.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {
    private String mail;
    private String password;
}
