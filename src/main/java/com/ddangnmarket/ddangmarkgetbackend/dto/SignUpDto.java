package com.ddangnmarket.ddangmarkgetbackend.dto;

import lombok.Data;

@Data
public class SignUpDto {
    private String nickname;

    private String phone;

    private String mail;

    private String password;
}
