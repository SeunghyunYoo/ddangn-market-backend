package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
public class Purchase {

    @Id @GeneratedValue
    @Column(name = "purchase_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
