package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class PostRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String desc;

    @NotEmpty
    @Min(value = 100)
    private int price;

    @NotEmpty
    private CategoryTag categoryTag;

}
