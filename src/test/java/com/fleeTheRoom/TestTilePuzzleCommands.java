package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.puzzles.ExitPuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle.*;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestTilePuzzleCommands {
    private TilesPuzzle tilesPuzzle;
    private Tile[] tiles;
    @Before
    public void initTest() {
        Tile tile1 = new Tile(0,0,2,2);
        Tile tile2 = new Tile(2,0,2,2);
        Tile tile3 = new Tile(0,2,2,2);
        Tile tile4 = new Tile(2,2,2,2);
        this.tiles = new Tile[]{tile1, tile2, tile3, tile4};
        this.tilesPuzzle = new TilesPuzzle(7, 7, 4, 4, 0, this.tiles);
    }

    @Test
    public void TestAdvanceInfoCommand() {
        tilesPuzzle.setState(TilesPuzzle.STATE.INFO);
        Command cmd = new AdvanceTileInfoCommand(tilesPuzzle);
        cmd.execute();
        assertEquals(tilesPuzzle.getState(), TilesPuzzle.STATE.PUZZLE);

        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLECOMP);
        cmd.execute();
        assertEquals(tilesPuzzle.getState(), TilesPuzzle.STATE.COMPLETE);
    }

    @Test
    public void TestMoveTileCommands() {
        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLE);

        Position oldPos = new Position(3,3);
        tilesPuzzle.getActiveTile().setPosition(oldPos);

        Command cmd = new MoveTileDownCommand(tilesPuzzle);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile().getPosition(), oldPos.down());

        oldPos = tilesPuzzle.getActiveTile().getPosition();
        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile().getPosition(), oldPos);

        cmd = new MoveTileUpCommand(tilesPuzzle);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile().getPosition(), oldPos.up());
        oldPos = tilesPuzzle.getActiveTile().getPosition();

        cmd = new MoveTileLeftCommand(tilesPuzzle);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile().getPosition(), oldPos.left());
        oldPos = tilesPuzzle.getActiveTile().getPosition();

        cmd = new MoveTileRightCommand(tilesPuzzle);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile().getPosition(), oldPos.right());
    }

    @Test
    public void TestSwitchTileCommand() {
        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLE);

        assertEquals(tilesPuzzle.getActiveTile(), this.tiles[0]);

        Command cmd = new SwitchTileCommand(tilesPuzzle);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile(), this.tiles[1]);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile(), this.tiles[2]);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile(), this.tiles[3]);

        cmd.execute();
        assertEquals(tilesPuzzle.getActiveTile(), this.tiles[0]);
    }

    @Test
    public void TestExitPuzzle() {
        tilesPuzzle.setActive(true);
        assertTrue(tilesPuzzle.isActive());

        Command cmd = new ExitPuzzleCommand(tilesPuzzle);
        cmd.execute();

        assertFalse(tilesPuzzle.isActive());
    }

}
