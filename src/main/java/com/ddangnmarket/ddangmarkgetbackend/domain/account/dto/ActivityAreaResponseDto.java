package com.ddangnmarket.ddangmarkgetbackend.domain.account.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import lombok.Data;

import java.util.List;

@Data
public class ActivityAreaResponseDto {
    private List<Dong> activityAreas;
    private int range;

    public ActivityAreaResponseDto(List<Dong> activityAreas, int range){
        this.activityAreas = activityAreas;
        this.range = range;
    }
}
