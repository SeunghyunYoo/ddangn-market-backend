package com.ddangnmarket.ddangmarkgetbackend.domain.district;

import com.ddangnmarket.ddangmarkgetbackend.domain.account.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DistrictServiceTest {

    @Autowired DistrictService districtService;
    @Autowired
    AccountService accountService;



}