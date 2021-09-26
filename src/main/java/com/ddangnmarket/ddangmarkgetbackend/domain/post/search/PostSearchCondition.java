package com.ddangnmarket.ddangmarkgetbackend.domain.post.search;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import com.ddangnmarket.ddangmarkgetbackend.domain.post.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author SeunghyunYoo
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSearchCondition {
    private String title;
    private String desc;
    private Integer priceGoe;
    private Integer priceLoe;
    private List<CategoryTag> categoryTags;
    private List<PostStatus> postStatuses;

//    public PostSearchCondition(String title, String desc, Integer priceGoe, Integer priceLoe) {
//        this.title = title;
//        this.desc = desc;
//        this.priceGoe = priceGoe;
//        this.priceLoe = priceLoe;
//        this.categoryTags = List.of();
//    }
//
//    public PostSearchCondition(String title, String desc, Integer priceGoe, Integer priceLoe, List<CategoryTag> categoryTags) {
//        this.title = title;
//        this.desc = desc;
//        this.priceGoe = priceGoe;
//        this.priceLoe = priceLoe;
//        this.categoryTags = categoryTags;
//    }
}
