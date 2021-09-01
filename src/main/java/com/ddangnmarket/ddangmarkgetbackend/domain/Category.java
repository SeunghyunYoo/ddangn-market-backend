package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.*;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

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
