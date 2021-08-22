package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import java.util.Arrays;

@Entity
@Getter
@Setter
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = false)
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
