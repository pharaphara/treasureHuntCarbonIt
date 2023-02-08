package com.pharaphara.treasureMap.Services;

import com.pharaphara.treasureMap.entity.*;
import com.pharaphara.treasureMap.entity.constant.Move;
import com.pharaphara.treasureMap.entity.constant.Orientation;
import com.pharaphara.treasureMap.exception.ApiRequestException;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * The DeserializeServiceImpl is responsible for parsing an input string in the specified format and transforming it into a GameData object
 * that contains all the data necessary to play the treasure map game. The class uses regex expressions to match and extract the relevant data
 * from the input string, such as map size, mountains, treasures, and adventurers.The class also performs logic validation, such as checking
 * if adventurers are within the bounds of the map and if there are no conflicts with other elements on the map.
 * If any errors are found, an ApiRequestException will be thrown.
 */
@Service
public class DeserializeServiceImpl implements DeserializeService {


    //expected map format : C - 3 - 4
    private final String MAP_REGEX = "C\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*$";

    //expected mountain format : M - 1 - 2
    private final String MOUNTAIN_REGEX = "M\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*$";

    //expected treasure format : T - 0 - 3 - 2
    private final String TREASURE_REGEX = "T\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*$";

    //expected adventurer format : A - Lara - 1 - 1 - S - AADADAGGA
    private final String ADVENTURER_REGEX = "A\\s*-\\s*\\w*\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*-\\s*[NSEO]\\s*-\\s*[AGD]*\\s*$";

    //Composite regex of the 4 previous with a negative lookahead to match line that doesn't fit the required format
    private final String VALID_FORMAT_REGEX = "^(?![CM]\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*$|T\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*$|A\\s*-\\s*\\w*\\s*-\\s*\\d*\\s*-\\s*\\d*\\s*-\\s*[NSEO]\\s*-\\s*[AGD]*\\s*$).*$";

    private static List<String> getMatch(String string, String regex) {
        final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(string);
        List<String> matchList = new ArrayList<>();

        while (matcher.find()) {
            matchList.add(matcher.group(0));
        }
        return matchList;
    }

    @Override
    public GameData deserialize(String string) {

        InputValidation(string);
        GameData gameData = new GameData();
        gameData.setMapSize(extractMap(string));
        gameData.setMountainList(extractMountains(string));
        gameData.setTreasureList(extractTreasures(string));
        gameData.setAdventurerList(extractAdventurers(string));
        gameData.setNumberOfTurn(gameData.getAdventurerList().stream().mapToInt(value -> value.getMoveList().size()).max().orElseThrow(NoSuchElementException::new));
        logicValidation(gameData);
        return gameData;
    }

    private void logicValidation(GameData gameData) {
        //todo check if adventurers is inside the map
        //number on case vs mountain etc
    }

    private void InputValidation(String string) {
        List<String> matchList = getMatch(string, VALID_FORMAT_REGEX);
        if (matchList.size() > 0) {
            throw new ApiRequestException("Input file is not valid : " + matchList);
        }
    }

    private List<Adventurer> extractAdventurers(String string) {
        List<String> matchList = getMatch(string, ADVENTURER_REGEX);

        List<Adventurer> adventurerList = new ArrayList<>();

        matchList.forEach(adventurerString -> {
            String[] mapTab = adventurerString.split("-");

            //0 is the letter T, 1 is the name , 2 is the x, 3 is the y, 4 is the orientation, 5 is the move list
            String name = mapTab[1].trim();
            Point position = new Point(Integer.parseInt(mapTab[2].trim()), Integer.parseInt(mapTab[3].trim()));
            Orientation orientation = Orientation.valueOf(mapTab[4].trim().toUpperCase());
            List<Move> moveList = mapTab[5].trim()
                    // Convert from String to IntStream
                    .chars()
                    // Convert IntStream to Stream<Character>
                    .mapToObj(c -> (char) c)
                    //Change type to move
                    .map(character -> Move.valueOf(String.valueOf(character).toUpperCase()))
                    // Collect the elements as a List Of Move
                    .toList();

            adventurerList.add(new Adventurer(position, name, orientation, moveList));
        });

        return adventurerList;

    }

    private List<Treasure> extractTreasures(String string) {
        List<String> matchList = getMatch(string, TREASURE_REGEX);

        List<Treasure> treasureList = new ArrayList<>();

        matchList.forEach(mountainString -> {
            String[] mapTab = mountainString.split("-");
            //0 is the letter T, 1 is the X , 2 is the Y, 3 the number of treasure
            treasureList.add(new Treasure(Integer.parseInt(mapTab[1].trim()), Integer.parseInt(mapTab[2].trim()), Integer.parseInt(mapTab[3].trim())));
        });

        return treasureList;
    }

    private List<Point> extractMountains(String string) {

        List<String> matchList = getMatch(string, MOUNTAIN_REGEX);

        List<Point> mountainList = new ArrayList<>();

        matchList.forEach(mountainString -> {
            String[] mapTab = mountainString.split("-");
            //0 is the letter M, 1 is the X , 2 is the Y
            mountainList.add(new Point(Integer.parseInt(mapTab[1].trim()), Integer.parseInt(mapTab[2].trim())));
        });

        return mountainList;

    }

    private Point extractMap(String string) {


        List<String> matchList = getMatch(string, MAP_REGEX);
        if (matchList.size() == 0) {
            throw new ApiRequestException("Input Data did not contain Map Info");
        }
        if (matchList.size() > 1) {
            throw new ApiRequestException("Input Data contains more than one map");
        }
        //there's only one map
        String[] mapTab = matchList.get(0).split("-");

        //0 is the letter C, 1 is the X size, 2 is the Y size
        return new Point(Integer.parseInt(mapTab[1].trim()), Integer.parseInt(mapTab[2].trim()));

    }
}
