package com.ddangnmarket.ddangmarkgetbackend.login;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SessionInfo {

    @Id @GeneratedValue
    @Column(name = "seesion_id")
    private Long id;

    private Long UserId;
}
