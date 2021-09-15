package com.ddangnmarket.ddangmarkgetbackend.domain.post;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.fasterxml.jackson.annotation.JsonCreator;

public enum PostStatus {
    NEW, RESERVE, COMPLETE;

    @JsonCreator
    public static PostStatus fromString(String key){
        for(PostStatus postStatus : PostStatus.values()){
            if(postStatus.name().equalsIgnoreCase(key)){
                return postStatus;
            }
        }
        throw new IllegalArgumentException("존재하지 않는 PostStatus 입니다.");
    }
}
