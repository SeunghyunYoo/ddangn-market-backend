package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Dong {
    BUNDANG, SUNAE, JEONGJA, SEOHYEON, IMAE,
    YATAP, GEUMGOK, GUMI, PANGYO, SAMPYEONG, UNJUNG;

    @JsonCreator
    public static Dong fromString(String key){
        for(Dong dong : Dong.values()){
            if(dong.name().equalsIgnoreCase(key)){
                return dong;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 동 주소입니다.");
    }
}
