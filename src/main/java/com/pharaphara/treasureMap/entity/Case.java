package com.pharaphara.treasureMap.entity;

public class Case {

    private boolean isMountain=false;
    private Adventurer adventurer=null;

    private int leftTreasure=0;

    public Case() {
    }

    public boolean isMountain() {
        return isMountain;
    }

    public void setMountain(boolean mountain) {
        isMountain = mountain;
    }

    public Adventurer getAdventurer() {
        return adventurer;
    }

    public void setAdventurer(Adventurer adventurer) {
        this.adventurer = adventurer;
    }

    public int getLeftTreasure() {
        return leftTreasure;
    }

    public void setLeftTreasure(int leftTreasure) {
        this.leftTreasure = leftTreasure;
    }

    public void takeTreasure(){this.leftTreasure=leftTreasure-1;}
}
