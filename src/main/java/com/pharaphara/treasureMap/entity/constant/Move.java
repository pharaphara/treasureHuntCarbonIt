package com.pharaphara.treasureMap.entity.constant;

/**

 Enumeration representing the moves (A, D and G) that can be made by an adventurer
 @author Pharaphara

 */
public enum Move {

    A(0), D(1), G(-1);


    /**
     * The angular  value of the move.
     * Positive value represents a move of 90 degree clockwise.
     * Negative value represents a move of 90 degree counter-clockwise.
     * Zero value represents a move of 0 degree.
     */
    private final int angularMoveValue;


    /**
     * Constructs a new Move object with the specified angular move value.
     *
     * @param angularMoveValue the angular move value of the move.
     */
    Move(int angularMoveValue) {
        this.angularMoveValue = angularMoveValue;
    }


    /**
     * Returns the angular move value of the move.
     *
     * @return the angular move value of the move.
     */
    public int getAngularMoveValue() {
        return angularMoveValue;
    }
}
