package com.pharaphara.treasureMap.entity.constant;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

/**
 * Enum representing the possible orientations of an adventurer on a treasure map.
 * The orientation is represented by an integer value and is used to determine the
 * next position of the adventurer. Origin is in the left hand corner.
 *
 * @author pharaphara
 */
public enum Orientation {
    /**
     * North orientation with an angular value of 0.
     */
    N(0) {
        /**
         * Calculates the next position of the adventurer given their current position.
         * When facing North, the next position will have the same x coordinate, but a y coordinate that is 1 less.
         *
         * @param actualPosition the current position of the adventurer
         * @return the next position of the adventurer
         */
        @Override
        public Point getNextPosition(Point actualPosition) {
            return new Point(actualPosition.x, actualPosition.y - 1);
        }
    },
    /**
     * East orientation with an angular value of 1.
     */
    E(1) {
        /**
         * Calculates the next position of the adventurer given their current position.
         * When facing East, the next position will have the same y coordinate, but an x coordinate that is 1 greater.
         *
         * @param actualPosition the current position of the adventurer
         * @return the next position of the adventurer
         */
        @Override
        public Point getNextPosition(Point actualPosition) {
            return new Point(actualPosition.x + 1, actualPosition.y);
        }
    },
    /**
     * South orientation with an angular value of 2.
     */
    S(2) {

        /**
         * Calculates the next position of the adventurer given their current position.
         * When facing South, the next position will have the same x coordinate, but a y coordinate that is 1 greater.
         *
         * @param actualPosition the current position of the adventurer
         * @return the next position of the adventurer
         */
        @Override
        public Point getNextPosition(Point actualPosition) {
            return new Point(actualPosition.x, actualPosition.y + 1);

        }
    },
    /**
     * West orientation with an angular value of 3.
     */
    O(3) {
        /**
         * Calculates the next position of the adventurer given their current position.
         * When facing West, the next position will have the same y coordinate, but an x coordinate that is 1 less.
         *
         * @param actualPosition the current position of the adventurer
         * @return the next position of the adventurer
         */
        @Override
        public Point getNextPosition(Point actualPosition) {
            return new Point(actualPosition.x - 1, actualPosition.y);
        }
    };

    /**
     * Map for storing the orientations by their angular value.
     */
    private static final Map<Integer, Orientation> BY_ORIENTATION = new HashMap<>();

    /**
     * Populates the map with the orientations and their corresponding angular values.
     */
    static {
        for (Orientation e : values()) {
            BY_ORIENTATION.put(e.AngularValue, e);

        }
    }

    private final int AngularValue;

    Orientation(int orient) {

        this.AngularValue = orient;
    }

    /**
     * Returns the orientation with the given angular value.
     * @param number the angular value of the orientation.
     * @return the orientation with the given angular value.
     */
    public static Orientation getByAngularValue(int number) {


        return BY_ORIENTATION.get(number);
    }

    /**
     * Returns the angular value of the orientation.
     * @return the angular value of the orientation.
     */
    public int getAngularValue() {
        return AngularValue;
    }

    /**
     * Returns the next position depending on the orientation and the actual position of the adventurer
     **/
    public abstract Point getNextPosition(Point actualPosition);
}

