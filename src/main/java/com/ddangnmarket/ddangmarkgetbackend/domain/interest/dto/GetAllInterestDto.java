package com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class GetAllInterestDto {
    private List<InterestDto> interests;

    public GetAllInterestDto(List<Interest> interests){
        this.interests = interests.stream().map(InterestDto::new).collect(Collectors.toList());
    }
}