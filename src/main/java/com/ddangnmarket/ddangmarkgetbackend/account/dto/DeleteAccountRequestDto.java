package com.ddangnmarket.ddangmarkgetbackend.account.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteAccountRequestDto {
    private String mail;
    private String password;
}
