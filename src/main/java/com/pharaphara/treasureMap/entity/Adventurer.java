package com.pharaphara.treasureMap.entity;

import com.pharaphara.treasureMap.entity.constant.Move;
import com.pharaphara.treasureMap.entity.constant.Orientation;

import java.awt.*;
import java.util.List;

public class Adventurer extends Point {

    private String name;
    private Point previousPosition;
    private int treasure=0;

    private Orientation orientation;

    private List<Move> moveList;

    public Adventurer(Point p, String name,  Orientation orientation, List<Move> moveList) {
        super(p);
        this.previousPosition=p;
        this.name = name;
        this.orientation = orientation;
        this.moveList = moveList;
    }

    public String getName() {
        return name;
    }

    public Point getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Point previousPosition) {
        this.previousPosition = previousPosition;
    }

    public int getTreasure() {
        return treasure;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }
    public List<Move> getMoveList() {
        return moveList;
    }

    public void takeTreasure(){this.treasure++;}
}
