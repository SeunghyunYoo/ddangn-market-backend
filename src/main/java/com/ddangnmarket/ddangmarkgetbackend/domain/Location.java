package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter
public class Location {

    @Id @GeneratedValue
    @Column(name = "location_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private LocationTag locationTag;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

}
