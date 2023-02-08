package com.pharaphara.treasureMap.Services;

import com.pharaphara.treasureMap.entity.GameData;
import com.pharaphara.treasureMap.entity.constant.Orientation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GameServiceImplTest {
    @Autowired
    private DeserializeService deserializeService;
    @Autowired
    private GameService gameService;

    @Test
    void testInputFromPdf() {

        //given
        final String input = "C - 3 - 4\n" +
                "M - 1 - 0\n" +
                "M - 2 - 1\n" +
                "T - 0 - 3 - 2\n" +
                "T - 1 - 3 - 3\n" +
                "A - Lara - 1 - 1 - S - AADADAGGA";

        //when
        GameData gameData = gameService.play(deserializeService.deserialize(input));

        //expected
        assertEquals(2,gameData.getTreasureList().stream().filter(treasure -> treasure.getLocation().equals(new Point(1,3))).findFirst().get().getLeftTreasure());
        assertEquals(0,gameData.getTreasureList().stream().filter(treasure -> treasure.getLocation().equals(new Point(0,3))).findFirst().get().getLeftTreasure());
        assertEquals(new Point(0,3),gameData.getAdventurerList().get(0).getLocation());
        assertEquals(Orientation.S,gameData.getAdventurerList().get(0).getOrientation());
        assertEquals(3,gameData.getAdventurerList().get(0).getTreasure());

    }

    @Test
    void laraShouldEndUpInTheSameLocation() {

        //given
        final String input = "C - 5 - 5\n" +
                "M - 1 - 4\n" +
                "M - 4 - 3\n" +
                "M - 3 - 0\n" +
                "M - 0 - 1\n" +
                "A - Lara - 1 - 3 - E - AADAAGAAAAGAAAAAAGAAAAAAGAAAAAAAAA\n"+
                "A - Alex - 2 - 3 - S - GGGGA";

        //when
        GameData gameData = gameService.play(deserializeService.deserialize(input));

        //expected
        assertEquals(new Point(1,3),gameData.getAdventurerList().get(0).getLocation());


    }
}