package com.fleeTheRoom;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class TestGame {
    private final String levelsPath = "src\\main\\resources\\levels\\levelTest";

    @Test
    public void TestResetProgress() {
        Game game = new Game();

        String savePath = "src\\main\\resources\\levels\\progressTest.txt";
        game.loadLevels(savePath, levelsPath);

        game.resetProgress();
        List<Map.Entry<Level, Boolean>> levels = game.getLevels();
        for(Map.Entry<Level,Boolean> level : levels) {
            assertFalse(level.getValue());
        }
    }
    @Test
    public void TestLoadSpecificLevel() {
        Game game = new Game();
        Level level = game.loadSpecificLevel(levelsPath, 1);

        assertEquals(level.getObjective().getPosition(), new Position(4, 1));
        assertEquals(level.getPlayerPos(), new Position(2, 2));
        assertEquals(level.getHeight(), 5);
        assertEquals(level.getWidth(), 5);
    }
}
