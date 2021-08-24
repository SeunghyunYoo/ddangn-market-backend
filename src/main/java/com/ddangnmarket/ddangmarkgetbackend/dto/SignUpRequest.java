package com.ddangnmarket.ddangmarkgetbackend.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    private String nickname;

    private String phone;

    private String mail;

    private String password;
}
