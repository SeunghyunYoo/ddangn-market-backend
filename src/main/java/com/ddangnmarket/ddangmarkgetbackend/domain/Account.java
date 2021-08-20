package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter @Setter
public class Account {
    @Id @GeneratedValue
    @Column(name = "account_id")
    private Long id;

    @Column(name = "account_name")
    private String name;

    private String email;

    private String phone;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Item> saleItems = new ArrayList<>();

    // oneToMany 단방향 관계에서 -> ManyToOne 양방향 관계
    // 멤버의 locationTag를 추가해주고 싶으면
    // new Location 생성해서, 여기에 Tag와 자기자신을 넣어 set해주고, add
    @OneToMany(mappedBy = "account")
    private Set<Location> locations = new HashSet<>();


    public void setAddress(Address address){
        this.address = address;
        LocationTag dong = address.getDong();
        addLocation(dong);
    }

    // 연관관계 메서드
    public void addLocation(LocationTag locationTag) {
        Set<Location> locations = this.getLocations();
        Location location = new Location();
        location.setLocationTag(locationTag);
        location.setAccount(this);
        locations.add(location);
    }

    public void addSaleItem(Item item){
        saleItems.add(item);
    }

}
