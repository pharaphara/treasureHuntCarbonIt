package com.pharaphara.treasureMap.Services;

import com.pharaphara.treasureMap.entity.Adventurer;
import com.pharaphara.treasureMap.entity.GameData;
import com.pharaphara.treasureMap.entity.constant.Move;
import com.pharaphara.treasureMap.entity.constant.Orientation;
import com.pharaphara.treasureMap.exception.ApiRequestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeserializeServiceTest {

    @Autowired
    private DeserializeService deserializeService;


    @Test
    void thisStringShouldSerializeWithoutError() {

        //given
        final String input = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA\n" +
                "A - Alex - 2 - 3 - N - AAGAA";

        //when
        GameData gameData = deserializeService.deserialize(input);
        Adventurer adventurer1 = gameData.getAdventurerList().get(0);
        Adventurer adventurer2 = gameData.getAdventurerList().get(1);

        //expected
        assertEquals(new Point(3, 4), gameData.getMapSize());

        assertEquals(2, gameData.getMountainList().size());
        assertTrue(gameData.getMountainList().stream().anyMatch(point -> point.equals(new Point(1, 0))));
        assertTrue(gameData.getMountainList().stream().anyMatch(point -> point.equals(new Point(2, 1))));
        assertFalse(gameData.getMountainList().stream().anyMatch(point -> point.equals(new Point(0, 3))));

        assertEquals(2, gameData.getTreasureList().size());
        assertEquals(2, gameData.getTreasureList().stream().filter(point -> point.equals(new Point(0, 3))).findFirst().get().getLeftTreasure());
        assertEquals(3, gameData.getTreasureList().stream().filter(point -> point.equals(new Point(1, 3))).findFirst().get().getLeftTreasure());
        assertFalse(gameData.getTreasureList().stream().anyMatch(point -> point.equals(new Point(2, 1))));

        assertEquals(2, gameData.getAdventurerList().size());
        assertTrue(gameData.getAdventurerList().stream().anyMatch(adventurer -> Objects.equals(adventurer.getName(), "Lara")));
        assertTrue(gameData.getAdventurerList().stream().anyMatch(adventurer -> Objects.equals(adventurer.getName(), "Alex")));
        assertEquals(new Point(1, 1), adventurer1.getLocation());
        assertEquals(new Point(2, 3), adventurer2.getLocation());
        assertEquals(Orientation.S, adventurer1.getOrientation());
        assertEquals(Orientation.N, adventurer2.getOrientation());
        assertEquals(9, adventurer1.getMoveList().size());
        assertEquals(5, adventurer2.getMoveList().size());
        assertEquals(Move.D, adventurer1.getMoveList().get(2));
        assertEquals(Move.G, adventurer2.getMoveList().get(2));
        assertNotEquals(Move.D, adventurer1.getMoveList().get(8));
        assertNotEquals(Move.D, adventurer1.getMoveList().get(1));

    }


    @Test
    void inputWithNoMapShouldTrowException() {

        //given
        final String input = "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA\n" +
                "A - Alex - 2 - 3 - N - AAGAA";

        Throwable exception = assertThrows(ApiRequestException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                deserializeService.deserialize(input);

            }
        });

        assertEquals("Input Data did not contain Map Info", exception.getMessage());

    }

    @Test
    void inputWithMoreThanOneMapShouldTrowException() {


        //given
        final String input = "C - 3 - 4\n" +
                "C - 5 - 8\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA\n" +
                "A - Alex - 2 - 3 - N - AAGAA";

        Throwable exception = assertThrows(ApiRequestException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                deserializeService.deserialize(input);

            }
        });

        assertEquals("Input Data contains more than one map", exception.getMessage());

    }

    @Test
    void invalidInputShouldTrowException() {

        //given
        final String input = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "X - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA\n" +
                "B - Alex - 2 - 3 - N - AAGAA";

        Throwable exception = assertThrows(ApiRequestException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                deserializeService.deserialize(input);

            }
        });

        assertEquals("Input file is not valid : [X - 0 - 3 - 2, B - Alex - 2 - 3 - N - AAGAA]", exception.getMessage());

    }
}