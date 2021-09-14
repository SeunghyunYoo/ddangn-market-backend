package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.district.Dong;
import com.ddangnmarket.ddangmarkgetbackend.domain.district.Position;
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

    private Position position;

    public District(Dong dong, Position position){
        this.dong = dong;
        this.position = position;
    }

    public void changeDong(Dong dong){
        this.dong = dong;
    }


}
