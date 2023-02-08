package com.pharaphara.treasureMap.entity;

import java.awt.*;

public class Treasure extends Point {

    private int leftTreasure;

    public Treasure(int x, int y, int leftTreasure) {
        super(x, y);
        this.leftTreasure = leftTreasure;
    }

    public int getLeftTreasure() {
        return leftTreasure;
    }

    public void removeTreasure(){
        this.leftTreasure= leftTreasure-1;
    }


}
