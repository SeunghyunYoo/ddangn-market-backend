package com.ddangnmarket.ddangmarkgetbackend.domain.district;

import com.ddangnmarket.ddangmarkgetbackend.domain.Account;
import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class DistrictServiceV1 {

    private final DistrictJpaRepository districtJpaRepository;

    public List<Dong> getActivityAreas(Account account){
        List<District> districts = districtJpaRepository.findAll();

        District accountDistrict = account.getActivityArea().getDistrict();
        Integer range = account.getActivityArea().getRange();

        return districts.stream()
                .filter(district ->
                    accountDistrict.getPosition().calcDiff(district.getPosition()) <= range)
                .map(District::getDong)
                .collect(Collectors.toList());
    }
}
