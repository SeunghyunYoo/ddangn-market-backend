package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Dong {
    BUNDANG, SUNAE1, SUNAE2, SUNAE3, JEONGJA1, JEONGJA2, JEONGJA3,
    SEOHYEON1, SEOHYEON2, IMAE1, IMAE2,
    YATAP1, YATAP2, YATAP3, GEUMGOK, GUMI, GUMI1,
    PANGYO, SAMPYEONG, UNJUNG, BACKHYUN;

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
