package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
public class PurchaseHistory {

    @Id @GeneratedValue
    @Column(name = "purchase_history_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;

    private LocalDateTime purchaseDate;
}
