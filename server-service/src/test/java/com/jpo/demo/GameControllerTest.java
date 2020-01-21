package com.jpo.demo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameControllerTest {

    GameController gameController;

    {
        gameController = new GameController();
    }

    @Test
    void generatorLevelTest(){
        assertNotNull(gameController.getLevelPlatformsMessage());
    }

    @Test
    void generatorStarTest(){
        assertNotNull(gameController.generateStarPosition());
    }
}