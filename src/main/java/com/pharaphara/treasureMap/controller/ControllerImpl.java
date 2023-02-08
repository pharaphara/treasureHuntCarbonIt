package com.pharaphara.treasureMap.controller;

import com.pharaphara.treasureMap.Services.DeserializeService;
import com.pharaphara.treasureMap.Services.GameService;
import com.pharaphara.treasureMap.entity.GameData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The `ControllerImpl` class defines the endpoints for the treasure map game and delegates the game logic to other services.
 */
@RestController
public class ControllerImpl {

    @Autowired
    DeserializeService deserializeService;
    @Autowired
    GameService gameService;


    /**
     * Processes a new client's request to play the treasure map game.
     * Delegates the task of deserializing the input string to the `DeserializeService` and the task of playing the game to the `GameService`.
     *
     * @param string the input string that represents the game data.
     * @return a `ResponseEntity` object with the result of the game.
     */
    @PostMapping("api/play")
    public ResponseEntity<String> newClient(@RequestBody String string) {

        GameData gameData = deserializeService.deserialize(string);

        return ResponseEntity.ok(gameService.play(gameData).toString());
    }


}
