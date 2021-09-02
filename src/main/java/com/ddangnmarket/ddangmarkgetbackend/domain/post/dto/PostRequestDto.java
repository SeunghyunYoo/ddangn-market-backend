package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import lombok.Data;

@Data
public class PostRequestDto {
    private String title;

    private String desc;

    private int price;

    private CategoryTag categoryTag;

}
