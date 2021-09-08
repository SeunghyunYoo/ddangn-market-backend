package com.ddangnmarket.ddangmarkgetbackend.domain.district;

import com.ddangnmarket.ddangmarkgetbackend.domain.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long>, DistrictRepositoryCustom {

}
