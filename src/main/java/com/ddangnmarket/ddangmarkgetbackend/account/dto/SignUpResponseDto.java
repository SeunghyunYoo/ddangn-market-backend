package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class SignUpResponseDto {
    private String nickname;

    private String phone;

    private String mail;
}
