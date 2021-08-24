package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteAccountRequestDto {
    private String mail;
    private String password;
}
