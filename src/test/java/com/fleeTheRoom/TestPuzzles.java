package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle.AdvanceInfoCommand;
import com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle.BluePuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle.OrangePuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle.SwitchTileCommand;
import com.fleeTheRoom.controllers.levels.ColorSeqPuzzleController;
import com.fleeTheRoom.controllers.levels.TilesPuzzleController;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

import com.fleeTheRoom.view.puzzles.PuzzleGUI;
import com.fleeTheRoom.view.puzzles.colorSeq.ColorSeqInputHandler;
import com.fleeTheRoom.view.puzzles.tiles.TilesInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestPuzzles {
    @Test
    public void TestColorSeqGenerateSequence() {
        ColorSeqPuzzle colorSeqPuzzle = new ColorSeqPuzzle(5,5,0);
        assertEquals(colorSeqPuzzle.getSequence().size(), 0);
        colorSeqPuzzle.generateSequence();
        boolean correct = true;
        for(String s : colorSeqPuzzle.getSequence()) {
            if(!s.equals("#FFA500") && !s.equals("#FF0000") && !s.equals("#00FF00") &&
                    !s.equals("#0000FF")) {
                correct = false;
                break;
            }
        }
        assertTrue(correct);
        assertEquals(colorSeqPuzzle.getState(), ColorSeqPuzzle.STATE.INACTIVE);
        assertEquals(colorSeqPuzzle.getGuess().size(), 0);
    }
    @Test
    public void TestColorSeqPuzzle() throws IOException {
        ColorSeqPuzzle colorSeqPuzzle = new ColorSeqPuzzle(5,5,0);
        ColorSeqInputHandler puzzleInputHandler = new ColorSeqInputHandler(colorSeqPuzzle);
        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);
        colorSeqPuzzle.generateSequence();

        Mockito.when(screenMock.readInput()).thenReturn(new KeyStroke('g', false, false));
        puzzleInputHandler.getNextCommand(screenMock).execute();
        assertEquals(colorSeqPuzzle.getGuess().size(), 1);
        assertEquals(colorSeqPuzzle.getGuess().get(0), "#00FF00");

        Mockito.when(screenMock.readInput()).thenReturn(new KeyStroke('r', false, false));
        puzzleInputHandler.getNextCommand(screenMock).execute();
        assertEquals(colorSeqPuzzle.getGuess().size(), 2);
        assertEquals(colorSeqPuzzle.getGuess().get(1), "#FF0000");

        Mockito.when(screenMock.readInput()).thenReturn(new KeyStroke('x', false, false));
        puzzleInputHandler.getNextCommand(screenMock).execute();
        assertEquals(colorSeqPuzzle.getGuess().size(), 0);

        for(String s : colorSeqPuzzle.getSequence()) {
            colorSeqPuzzle.addGuess(s);
        }
        colorSeqPuzzle.checkSequence();

        assertEquals(colorSeqPuzzle.getState(), ColorSeqPuzzle.STATE.RIGHTGUESS);

        Mockito.when(screenMock.readInput()).thenReturn(new KeyStroke(KeyType.Enter));
        Command cmd = puzzleInputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof AdvanceInfoCommand);
        cmd.execute();
        assertEquals(ColorSeqPuzzle.STATE.COMPLETE, colorSeqPuzzle.getState());
    }
    @Test
    public void TestTilePuzzle() {
        Tile tile1 = new Tile(0,0,2,2);
        Tile tile2 = new Tile(2,0,2,2);
        Tile tile3 = new Tile(0,2,2,2);
        Tile tile4 = new Tile(2,2,2,2);
        Tile[] tiles = {tile1, tile2, tile3, tile4};

        TilesPuzzle tilesPuzzle = new TilesPuzzle(6,6, 4, 4, 0, tiles);
        assertEquals(tilesPuzzle.getActiveTile(), tile1);
        assertTrue(tile1.isActive());
        assertArrayEquals(tilesPuzzle.getTiles(), tiles);
    }
    @Test
    public void TestTilePuzzleSwitch() throws IOException {
        Tile tile1 = new Tile(0,0,2,2);
        Tile tile2 = new Tile(2,0,2,2);
        Tile tile3 = new Tile(0,2,2,2);
        Tile tile4 = new Tile(2,2,2,2);
        Tile[] tiles = {tile1, tile2, tile3, tile4};

        TilesPuzzle tilesPuzzle = new TilesPuzzle(6,6, 4, 4, 0, tiles);
        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLE);
        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);
        TilesInputHandler inputHandler = new TilesInputHandler(tilesPuzzle);
        KeyStroke input = new KeyStroke('e', false, false);
        Mockito.when(screenMock.readInput()).thenReturn(input);
        Command com = inputHandler.getNextCommand(screenMock);

        assertTrue(com instanceof SwitchTileCommand);
        com.execute();

        assertEquals(tilesPuzzle.getActiveTile(), tile2);
        assertTrue(tile2.isActive());
        assertFalse(tile1.isActive());

        inputHandler.getNextCommand(screenMock).execute();
        inputHandler.getNextCommand(screenMock).execute();
        inputHandler.getNextCommand(screenMock).execute();
        assertTrue(tile1.isActive());
    }

    @Test
    public void TestTilesCheckPos() {
        Tile tile1 = new Tile(0,0,2,2);
        Tile tile2 = new Tile(2,0,2,2);
        Tile tile3 = new Tile(0,2,2,2);
        Tile tile4 = new Tile(2,2,2,2);
        Tile[] tiles = {tile1, tile2, tile3, tile4};

        TilesPuzzle tilesPuzzle = new TilesPuzzle(6,6, 4, 4, 0, tiles);
        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLE);

        tilesPuzzle.checkPositions();
        assertNotEquals(TilesPuzzle.STATE.PUZZLECOMP, tilesPuzzle.getState());

        for(Tile tile : tiles) {
            tile.setPosition(tile.getInitPos());
        }

        tilesPuzzle.checkPositions();
        assertEquals(TilesPuzzle.STATE.PUZZLECOMP, tilesPuzzle.getState());

        tilesPuzzle.setState(TilesPuzzle.STATE.PUZZLE);

        Position[] positions = new Position[tiles.length];

        for(int i = 0; i < tiles.length; ++i) {
            positions[i] = tiles[i].getPosition();
        }

        for(int i = 0; i < tiles.length; ++i) {
            tiles[i].setEmpty(true);
            tiles[i].setPosition(positions[(i+1)%tiles.length]);
        }

        tilesPuzzle.checkPositions();
        assertEquals(TilesPuzzle.STATE.PUZZLECOMP, tilesPuzzle.getState());

    }

    @Test
    public void TestTilesPuzzleController() throws IOException {
        TilesPuzzle tilesPuzzle = Mockito.mock(TilesPuzzle.class);
        TilesPuzzleController tilesPuzzleController = new TilesPuzzleController(tilesPuzzle);
        PuzzleGUI puzzleGUI = Mockito.mock(PuzzleGUI.class);

        Mockito.when(puzzleGUI.getNextCommand()).thenReturn(new SwitchTileCommand(tilesPuzzle));


        tilesPuzzleController.initPuzzle();
        Mockito.verify(tilesPuzzle).setActive(true);
        Mockito.verify(tilesPuzzle).setState(TilesPuzzle.STATE.INFO);

        Mockito.when(tilesPuzzle.getState()).thenReturn(TilesPuzzle.STATE.INFO);
        tilesPuzzleController.update(tilesPuzzle.getState(), puzzleGUI);
        Mockito.verify(puzzleGUI,Mockito.times(1)).draw();

        Mockito.when(tilesPuzzle.getState()).thenReturn(TilesPuzzle.STATE.PUZZLE);
        tilesPuzzleController.update(tilesPuzzle.getState(), puzzleGUI);
        Mockito.verify(puzzleGUI,Mockito.times(2)).draw();
        Mockito.verify(tilesPuzzle,Mockito.times(1)).checkPositions();
        Mockito.verify(tilesPuzzle,Mockito.times(1)).switchActive();
    }

    @Test
    public void TestColorSeqController() throws IOException {
        ColorSeqPuzzle puzzle = new ColorSeqPuzzle(10,10,0);
        PuzzleGUI puzzleGUI = Mockito.mock(PuzzleGUI.class);

        ColorSeqPuzzle puzzleSpy = Mockito.spy(puzzle);
        ColorSeqPuzzleController controller = new ColorSeqPuzzleController(puzzleSpy);

        assertEquals(ColorSeqPuzzle.STATE.INACTIVE, puzzleSpy.getState());

        controller.initPuzzle();
        assertEquals(ColorSeqPuzzle.STATE.INFO, puzzleSpy.getState());
        assertTrue(puzzleSpy.isActive());
        Mockito.verify(puzzleSpy, Mockito.times(1)).generateSequence();


        Mockito.when(puzzleGUI.getNextCommand()).thenReturn(new AdvanceInfoCommand(puzzleSpy));

        controller.update(puzzleSpy.getState(), puzzleGUI);
        assertEquals(ColorSeqPuzzle.STATE.SEQUENCE, puzzleSpy.getState());

        controller.update(puzzleSpy.getState(), puzzleGUI);
        assertEquals(ColorSeqPuzzle.STATE.GUESS, puzzleSpy.getState());

        Mockito.when(puzzleGUI.getNextCommand()).thenReturn(new BluePuzzleCommand(puzzleSpy));

        controller.update(puzzleSpy.getState(), puzzleGUI);
        assertEquals(ColorSeqPuzzle.STATE.GUESS, puzzleSpy.getState());
        assertEquals(1, puzzleSpy.getGuess().size());
        assertEquals("#0000FF", puzzleSpy.getGuess().get(0));

        controller.update(puzzleSpy.getState(), puzzleGUI);
        assertEquals(ColorSeqPuzzle.STATE.GUESS, puzzleSpy.getState());
        assertEquals(2, puzzleSpy.getGuess().size());
        assertEquals("#0000FF", puzzleSpy.getGuess().get(0));
        assertEquals("#0000FF", puzzleSpy.getGuess().get(1));

        Mockito.when(puzzleGUI.getNextCommand()).thenReturn(new OrangePuzzleCommand(puzzleSpy));
        controller.update(puzzleSpy.getState(), puzzleGUI);
        assertEquals(ColorSeqPuzzle.STATE.GUESS, puzzleSpy.getState());
        assertEquals(0, puzzleSpy.getGuess().size());
        Mockito.verify(puzzleSpy, Mockito.times(1)).checkSequence();
        
    }
}
