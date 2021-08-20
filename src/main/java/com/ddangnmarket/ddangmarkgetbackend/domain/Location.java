package com.ddangnmarket.ddangmarkgetbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    // cascade option을 account에서 가져가서 json ignore해줘야 함
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Override
    public String toString() {
        return "Location{" +
                "id=" + id +
                ", locationTag=" + locationTag +
                ", account=" + account +
                '}';
    }
}
