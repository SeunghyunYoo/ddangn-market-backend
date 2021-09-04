package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ActivityArea;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import com.ddangnmarket.ddangmarkgetbackend.domain.Dong;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetAccountInfoResponseDto {
    private String mail;
    private String nickname;
    private String phone;
    private List<Dong> activeAreas;

    public GetAccountInfoResponseDto(Account account){
        this.mail = account.getMail();
        this.nickname = account.getNickname();
        this.phone = account.getPhone();
        this.activeAreas = account.getActivityAreas().stream()
                .map(ActivityArea::getDistrict)
                .map(District::getDong)
                .collect(Collectors.toList());
    }

}
