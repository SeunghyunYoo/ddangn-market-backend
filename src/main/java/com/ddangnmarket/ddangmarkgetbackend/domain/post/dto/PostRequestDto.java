package com.ddangnmarket.ddangmarkgetbackend.domain.post.dto;

import com.ddangnmarket.ddangmarkgetbackend.domain.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.category.validation.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class PostRequestDto {
    @NotEmpty
    private String title;

    @NotEmpty
    private String desc;

    @Min(value = 100)
    private int price;

    private CategoryTag categoryTag;

    private List<Long> fileIds = new ArrayList<>();

    public PostRequestDto(String title, String desc, int price, CategoryTag categoryTag){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.categoryTag = categoryTag;
    }

    public PostRequestDto(String title, String desc, int price, CategoryTag categoryTag, List<Long> fileIds){
        this.title = title;
        this.desc = desc;
        this.price = price;
        this.categoryTag = categoryTag;
        this.fileIds.addAll(fileIds);
    }

}
