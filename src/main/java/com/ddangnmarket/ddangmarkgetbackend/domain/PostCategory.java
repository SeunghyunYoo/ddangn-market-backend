package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
public class PostCategory {
    @Id @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;

    @OneToOne(mappedBy = "postCategory")
    private Post post;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
