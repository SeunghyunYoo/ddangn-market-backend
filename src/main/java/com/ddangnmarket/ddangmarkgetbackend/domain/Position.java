package com.ddangnmarket.ddangmarkgetbackend.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Position {
    private int x;
    private int y;

    public Position(int x, int y){
        this.x = x;
        this.y = y;
    }

    public Integer calcDiff(Position position){
       return (Math.abs(this.x - position.x) + Math.abs(this.y - position.y));
    }
}
