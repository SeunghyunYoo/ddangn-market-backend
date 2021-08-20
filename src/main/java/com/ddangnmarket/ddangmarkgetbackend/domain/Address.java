package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {
    private String si;
    private String gu;
    @Enumerated(EnumType.STRING)
    private LocationTag dong;

    public Address(LocationTag dong){
        si = "SeongNam";
        gu = "Bundang";
        this.dong = dong;
    }
}
