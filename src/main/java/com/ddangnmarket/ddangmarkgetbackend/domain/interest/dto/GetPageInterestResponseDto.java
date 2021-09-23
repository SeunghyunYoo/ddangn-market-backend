package com.ddangnmarket.ddangmarkgetbackend.domain.interest.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.Interest;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class GetPageInterestResponseDto {
    private List<InterestDto> interests;

    public GetPageInterestResponseDto(Page<Interest> pageInterests){
        List<Interest> interests = pageInterests.getContent();
        this.interests = interests.stream().map(InterestDto::new).collect(Collectors.toList());
    }
}
