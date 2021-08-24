package com.ddangnmarket.ddangmarkgetbackend.login.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class LoginResponseDto {
    private String nickname;
    private String JSESSIONID;

    public LoginResponseDto(String nickname){
        this.nickname = nickname;
    }

    public void setJSESSIONID(String JSESSIONID) {
        this.JSESSIONID = JSESSIONID;
    }
}
