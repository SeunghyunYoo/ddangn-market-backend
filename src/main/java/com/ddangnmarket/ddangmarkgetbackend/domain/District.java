package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class District extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "district_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Dong dong;

    public District(Dong dong){
        this.dong = dong;
    }
}
