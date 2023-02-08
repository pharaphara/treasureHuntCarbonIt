package com.pharaphara.treasureMap.entity;

import java.awt.*;
import java.util.List;

/**

 The GameData class represents a data model for a treasure hunt game.
 It contains the size of the map, a list of mountains, a list of treasures, and a list of adventurers.
 It also contains the number of turns for the game.
 @author Pharaphara
 */
public class GameData {

    private Point mapSize;
    private List<Point> mountainList;
    private List<Treasure> treasureList;
    private List<Adventurer> adventurerList;

    private int numberOfTurn;

    public GameData() {
    }

    public Point getMapSize() {
        return mapSize;
    }

    public void setMapSize(Point mapSize) {
        this.mapSize = mapSize;
    }

    public List<Point> getMountainList() {
        return mountainList;
    }

    public void setMountainList(List<Point> mountainList) {
        this.mountainList = mountainList;
    }

    public List<Treasure> getTreasureList() {
        return treasureList;
    }

    public void setTreasureList(List<Treasure> treasureList) {
        this.treasureList = treasureList;
    }

    public List<Adventurer> getAdventurerList() {
        return adventurerList;
    }

    public void setAdventurerList(List<Adventurer> adventurerList) {
        this.adventurerList = adventurerList;
    }


    public int getNumberOfTurn() {
        return numberOfTurn;
    }

    public void setNumberOfTurn(int numberOfTurn) {
        this.numberOfTurn = numberOfTurn;
    }

    @Override
    public String toString() {
        StringBuilder outputFile = new StringBuilder("C - " + mapSize.x + " - " + mapSize.y + "\r\n");
        mountainList.forEach(mountain -> outputFile.append("M - ").append(mountain.x).append(" - ").append(mountain.y).append("\r\n"));
        treasureList.stream().filter(treasure -> treasure.getLeftTreasure() > 0).forEach(treasure -> outputFile.append("T - ").append(treasure.x).append(" - ").append(treasure.y).append(" - ").append(treasure.getLeftTreasure()).append("\r\n"));
        adventurerList.forEach(adventurer -> {
            outputFile.append("A - ").append(adventurer.getName()).append(" - ").append(adventurer.x).append(" - ").append(adventurer.y).append(" - ").append(adventurer.getOrientation()).append(" - ").append(adventurer.getTreasure()).append("\r\n");

        });


        return outputFile.toString();
    }


}
