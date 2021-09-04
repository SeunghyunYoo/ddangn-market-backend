package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@EqualsAndHashCode(exclude = {"id", "account"}, callSuper = false)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ActivityArea extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "activity_area_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "district_id")
    @EqualsAndHashCode.Include
    private District district;

    public static ActivityArea createActiveArea(District district){
        ActivityArea activityArea = new ActivityArea();
        activityArea.district = district;
        return activityArea;
    }

    public void updateAccount(Account account){
        this.account = account;
    }
}
