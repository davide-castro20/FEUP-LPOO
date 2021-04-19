package com.fleeTheRoom;

import com.fleeTheRoom.controllers.creators.TilePuzzleCreator;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestPuzzleCreator {
    @Test
    public void TestTilePuzzleCreator() {
        String path = "src\\main\\resources\\tilePuzzles\\tilePuzzleTest.txt";
        TilePuzzleCreator creator = new TilePuzzleCreator(path, 0);
        TilesPuzzle puzzle = creator.createPuzzle();

        assertEquals(puzzle.getDifficulty(), 0);
        assertEquals(24, puzzle.getPictureWidth());
        assertEquals(12, puzzle.getPictureHeight());

        assertEquals(6, puzzle.getTiles().length);
        assertEquals(',', puzzle.getTiles()[1].getLine(1).charAt(7));
        assertEquals('/', puzzle.getTiles()[3].getLine(0).charAt(2));
        assertEquals(' ', puzzle.getTiles()[3].getLine(0).charAt(1));
        assertEquals('/', puzzle.getTiles()[3].getLine(0).charAt(3));

    }
}
