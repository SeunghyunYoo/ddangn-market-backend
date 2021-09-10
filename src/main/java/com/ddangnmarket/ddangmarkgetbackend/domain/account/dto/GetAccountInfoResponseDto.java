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
    private List<Dong> activityAreas;
    private int range;
    private double mannerTemp;

    public GetAccountInfoResponseDto(Account account, List<Dong> activityAreas){
        this.mail = account.getMail();
        this.nickname = account.getNickname();
        this.phone = account.getPhone();
        this.activityAreas = activityAreas;
        this.range = account.getActivityArea().getRange();
        this.mannerTemp = account.getMannerTemp();
    }

}
