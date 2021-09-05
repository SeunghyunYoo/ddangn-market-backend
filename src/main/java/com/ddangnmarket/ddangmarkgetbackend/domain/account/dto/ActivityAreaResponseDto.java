package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import lombok.Data;

import java.util.List;

@Data
public class ActivityAreaResponseDto {
    private List<Dong> activityAreas;

    public ActivityAreaResponseDto(List<Dong> activityAreas){
        this.activityAreas = activityAreas;
    }
}
