package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EqualsAndHashCode(exclude = {"id", "account"}, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class ActivityArea extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "activity_area_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "activityArea")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @EqualsAndHashCode.Include
    private District district;

    @EqualsAndHashCode.Include
    private Integer range;

    public static ActivityArea createActiveArea(District district, Integer range){
        ActivityArea activityArea = new ActivityArea();
        activityArea.district = district;
        activityArea.range = range;

        return activityArea;
    }

    public void changeActiveArea(District district, Integer range){
        this.district = district;
        this.range = range;
        account.changeActivityArea(this);
    }

    public void changeRange(Integer range){
        if(range < 4){
            this.range = range;
        } else{
            this.range = 3;
        }
    }

    public boolean isAccessibleArea(District district){
        return this.district.getPosition().calcDiff(district.getPosition()) <= this.range;
    }

    public void updateAccount(Account account){
        this.account = account;
    }
}
