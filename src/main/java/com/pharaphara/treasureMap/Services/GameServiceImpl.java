package com.pharaphara.treasureMap.Services;

import com.pharaphara.treasureMap.entity.*;
import com.pharaphara.treasureMap.entity.constant.Move;
import com.pharaphara.treasureMap.entity.constant.Orientation;
import org.springframework.stereotype.Service;

import java.awt.*;

/**
 * This code is an implementation of a game service in Java, which plays a treasure hunt game using a 3D matrix
 * to represent the map. The game starts by initializing a 3D matrix with all the cases and setting the locations of
 * the mountains and treasures on each layer. The game then spawns the adventurers on the first layer.
 * On each layer, the game moves each adventurer one by one, checking if they can pick up treasure in their current location,
 * and then either move them to the next position or keep them in the same location depending on whether the next move is possible.
 * The code also takes into account the orientation of the adventurer and rotates it accordingly when needed.
 * The game continues for all the turns specified in the game data and updates the locations of the adventurers and the amount of treasure left in each case
 */
@Service
public class GameServiceImpl implements GameService {

    @Override
    public GameData play(GameData gameData) {

        //create a 3d matrix  [array_index][row_index][column_index]
        //each array represent a turn
        int zIdx = gameData.getNumberOfTurn() + 1;// +1 because turn 0 is the starting position and not a real turn
        int xIdx = gameData.getMapSize().x;
        int yIdx = gameData.getMapSize().y;
        Case[][][] map = new Case[zIdx][xIdx][yIdx];

        //initialize all cases on all layers
        for (int z = 0; z < zIdx; z++) {
            for (int x = 0; x < xIdx; x++) {
                for (int y = 0; y < yIdx; y++) {
                    map[z][x][y] = new Case();
                }
            }
        }
        //setting mountains and treasures on all layers
        gameData.getMountainList().forEach(mountain -> {
            for (int i = 0; i < zIdx; i++) {
                map[i][mountain.x][mountain.y].setMountain(true);
            }
        });
        gameData.getTreasureList().forEach(treasure -> {
            for (int i = 0; i < zIdx; i++) {
                map[i][treasure.x][treasure.y].setLeftTreasure(treasure.getLeftTreasure());
            }

        });

        //spawning
        gameData.getAdventurerList().forEach(aventurier -> {
            map[0][aventurier.x][aventurier.y].setAdventurer(aventurier);
        });

        //to do on each layer/turn
        for (int z = 0; z < zIdx; z++) {

            //Move each adventurer ono by one
            for (Adventurer adventurer : gameData.getAdventurerList()) {
                //check if actual location contains a treasure and if it's possible to pick
                if (map[z][adventurer.x][adventurer.y].getLeftTreasure() > 0 && !adventurer.getPreviousPosition().equals(adventurer)) {
                    adventurer.takeTreasure();
                    gameData.getTreasureList().stream().filter(treasure -> treasure.equals(new Point(adventurer))).findFirst().get().removeTreasure();
                    //removes one treasure on all the next layers
                    for (int i = z + 1; i < zIdx; i++) {
                        map[i][adventurer.x][adventurer.y].takeTreasure();
                    }
                }

                Move nextMove = z < adventurer.getMoveList().size() ? adventurer.getMoveList().get(z) : null;
                if (nextMove == Move.A) {
                    boolean isNextMovePossible = false;
                    Point nextPosition = adventurer.getOrientation().getNextPosition(adventurer);
                    //check if next position is out of bound
                    if (nextPosition.x >= 0 && nextPosition.y >= 0 && nextPosition.x < xIdx && nextPosition.y < yIdx) {
                        Case nextCase = map[z][nextPosition.x][nextPosition.y];
                        isNextMovePossible = !nextCase.isMountain() && nextCase.getAdventurer() == null;
                    }
                    if (isNextMovePossible) {
                        adventurer.setPreviousPosition(adventurer.getLocation());
                        adventurer.setLocation(nextPosition);
                        //add adventurer on the next layer
                        map[z + 1][nextPosition.x][nextPosition.y].setAdventurer(adventurer);

                    } else {
                        map[z + 1][adventurer.x][adventurer.y].setAdventurer(adventurer);
                    }
                } else if (nextMove != null) {
                    Orientation currentOrientation = adventurer.getOrientation();
                    //calculate new orientation by keeping the remainder with modulo operation
                    // there's four possible orientations and they all have one angular value given in a clockwise order: N=0, E=1, S=2, O=3
                    // each rotation has one angular move value : D=1 because it's clockwise and G=-1 because it's counter clockwise
                    // then E+D is the same as 1+1 giving  2 and S is equal 2, and 2%4 is still equal  2
                    // Modulo is needed when passing from O to N or from N to O : O+D=4 and N+G=-1
                    // 4%4 = 0 = N and -1%4=3=Ouest
                    int angularValueOfNextOrientation = Math.floorMod(currentOrientation.getAngularValue() + nextMove.getAngularMoveValue(), Orientation.values().length);
                    adventurer.setOrientation(Orientation.getByAngularValue(angularValueOfNextOrientation));
                    //add adventurer on the next layer at same position
                    map[z + 1][adventurer.x][adventurer.y].setAdventurer(adventurer);
                    adventurer.setPreviousPosition(adventurer.getLocation());
                }

            }
            displayMap(xIdx, yIdx, map, z);
        }

        System.out.println(gameData);
        return gameData;
    }

    private static void displayMap(int xIdx, int yIdx, Case[][][] map, int i) {
        Case[][] displayMatrix = map[i];
        System.out.println("Tour " + i);
        System.out.print("X |");
        for (int k = 0; k < xIdx; k++) {
            System.out.print("  " + k + "  |");
        }
        System.out.print("\r\n");
        for (int y = 0; y < yIdx; y++) {
            System.out.print(y + " |");
            for (int x = 0; x < xIdx; x++) {

                if (displayMatrix[x][y].getAdventurer() != null) {
                    System.out.print(displayMatrix[x][y].getAdventurer().getName().charAt(0) + "x(" + displayMatrix[x][y].getAdventurer().getTreasure() + ")|");
                } else if (displayMatrix[x][y].isMountain()) {
                    System.out.print("M    |");
                } else if (displayMatrix[x][y].getLeftTreasure() > 0) {
                    System.out.print("T" + displayMatrix[x][y].getLeftTreasure() + "   |");
                } else {
                    System.out.print(".    |");
                }
            }
            System.out.print("\r\n");

        }
        System.out.print("  -");
        for (int j = 0; j < xIdx; j++) {
            System.out.print("------");
        }
        System.out.println();
    }

}
