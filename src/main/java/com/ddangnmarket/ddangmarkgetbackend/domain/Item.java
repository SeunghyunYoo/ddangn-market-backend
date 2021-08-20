package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @Column(name = "item_name")
    private String name;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account seller;

    @Enumerated(EnumType.STRING)
    private LocationTag locationTag;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime register_date;

    public Item(){
        this.itemStatus = ItemStatus.NOT_ASSIGNED;
        this.register_date = LocalDateTime.now();
    }

    public void setSeller(Account seller){
        this.seller = seller;
        seller.addSaleItem(this);
        locationTag = seller.getAddress().getDong();
    }
}
