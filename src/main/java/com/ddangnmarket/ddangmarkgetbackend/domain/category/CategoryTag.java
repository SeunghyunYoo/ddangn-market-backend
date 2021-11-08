package com.ddangnmarket.ddangmarkgetbackend.domain.category;


import com.fasterxml.jackson.annotation.JsonCreator;

public enum CategoryTag {
    //생활 가전, 가구, 의류, 도서, 디지털기기, 기타
    APPLIANCE, FURNITURE_INTERIOR, WOMEN_CLOTHES, BOOK_TICKET_RECORD, DIGITAL, OTHERS,
    SPORTS_LEISURE, WOMAN_ACCESSORIES, GAME_HOBBY, BEAUTY, PLANT,
    LIVING_PROCESSED_FOOD, PET_STUFF, MAN_FASHION_ACCESSORIES, CHILDREN_BOOK, BABY_PRODUCTS;

    @JsonCreator
    public static CategoryTag fromString(String key){
        for(CategoryTag categoryTag : CategoryTag.values()){
            if(categoryTag.name().equalsIgnoreCase(key)){
                return categoryTag;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 품목입니다.");
    }

}
