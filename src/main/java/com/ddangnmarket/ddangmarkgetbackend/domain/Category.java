package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.ddangnmarket.ddangmarkgetbackend.domain.category.CategoryTag;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category extends BaseEntity{

    @Id @GeneratedValue
    @Column(name= "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private CategoryTag categoryTag;

    public Category(CategoryTag categoryTag){
        this.categoryTag = categoryTag;
    }

}
