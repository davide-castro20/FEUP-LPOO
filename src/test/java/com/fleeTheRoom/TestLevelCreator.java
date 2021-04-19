package com.fleeTheRoom;

import com.fleeTheRoom.controllers.creators.LevelCreator;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TestLevelCreator {

    @Test
    public void TestCreateLevel() {
        LevelCreator levelCreator = new LevelCreator("src\\main\\resources\\levels\\levelTest1.txt");
        Level level = levelCreator.createLevel();

        assertEquals(level.getPlayerPos(), new Position(2, 2));
        assertEquals(level.getObjective().getPosition(), new Position(4, 1));

        List<Wall> walls = level.getWalls();
        assertEquals(walls.size(), 2);
        assertEquals(walls.get(0).getPosition(), new Position(1, 1));
        assertEquals(walls.get(1).getPosition(), new Position(3, 4));

        List<Door> doors = level.getDoors();
        assertEquals(doors.size(), 3);
        assertEquals(doors.get(0).getPosition(), new Position(0, 2));
        assertEquals(doors.get(1).getPosition(), new Position(4, 2));
        assertEquals(doors.get(2).getPosition(), new Position(1, 4));

        List<Key> keys = level.getKeys();
        assertEquals(keys.size(), 2);
        assertEquals(keys.get(0).getPosition(), new Position(2, 0));
        assertEquals(keys.get(1).getPosition(), new Position(3, 2));
    }
}
