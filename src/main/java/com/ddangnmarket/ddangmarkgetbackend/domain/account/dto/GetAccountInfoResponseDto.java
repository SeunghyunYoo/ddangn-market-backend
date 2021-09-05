package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class GetAccountInfoResponseDto {
    private String mail;
    private String nickname;
    private String phone;
    private List<Dong> activeAreas;

    public GetAccountInfoResponseDto(Account account, List<Dong> activeAreas){
        this.mail = account.getMail();
        this.nickname = account.getNickname();
        this.phone = account.getPhone();
        this.activeAreas = activeAreas;
    }

}
