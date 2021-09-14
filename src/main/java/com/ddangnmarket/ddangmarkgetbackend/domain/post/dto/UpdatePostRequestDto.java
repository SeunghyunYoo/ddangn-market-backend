package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePostRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String desc;

    @Min(value = 100)
    private int price;

    @NotEmpty
    private CategoryTag categoryTag;
}
