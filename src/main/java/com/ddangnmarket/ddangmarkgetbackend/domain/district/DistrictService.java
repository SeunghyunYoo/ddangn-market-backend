package com.ddangnmarket.ddangmarkgetbackend.domain.district;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.ActivityArea;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;

    public List<Dong> getAccountActivityAreas(Account account){
        List<District> districts = districtRepository.findAll();

        ActivityArea activityArea = account.getActivityArea();

        return districts.stream()
                .filter(activityArea::isAccessibleArea)
                .map(District::getDong)
                .collect(Collectors.toList());
    }

}
