package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.level.*;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


//TODO NEEDS MAJOR CHANGES
public class TestLevelCommands {
    private Level level;
    private Player player;

    @Before
    public void initTestCommands() {
        level = new Level(50,50, 0,0);
        player = new Player(10,10);
        Objective o = new Objective(30,30);
        level.addPlayer(player);
        level.addObjective(o);
    }

    @Test
    public void testMovePlayerDownCommand(){
        Position testPosition = new Position(10,10);
        Command cmd = new MovePlayerDownCommand(level);
        cmd.execute();
        assertEquals(player.getPosition(),testPosition.down());
    }

    @Test
    public void testMovePlayerUpCommand(){
        Position testPosition = new Position(10,10);
        Command cmd = new MovePlayerUpCommand(level);
        cmd.execute();
        assertEquals(player.getPosition(),testPosition.up());
    }

    @Test
    public void testMovePlayerRightCommand(){
        Position testPosition = new Position(10,10);
        Command cmd = new MovePlayerRightCommand(level);
        cmd.execute();
        assertEquals(player.getPosition(),testPosition.right());
    }

    @Test
    public void testMovePlayerLeftCommand(){
        Position testPosition = new Position(10,10);
        Command cmd = new MovePlayerLeftCommand(level);
        cmd.execute();
        assertEquals(player.getPosition(),testPosition.left());
    }
    @Test
    public void TestExitLevelCommand() {
        Command cmd = new ExitLevelCommand(level);
        cmd.execute();
        assertTrue(level.isNextLevel());
    }
}
