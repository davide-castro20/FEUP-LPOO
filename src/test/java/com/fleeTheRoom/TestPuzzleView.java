package com.fleeTheRoom;

import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;
import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import com.fleeTheRoom.view.puzzles.colorSeq.ColorSeqPuzzleView;
import com.fleeTheRoom.view.puzzles.findSeq.FindSeqPuzzleView;
import com.fleeTheRoom.view.puzzles.tiles.TileView;
import com.fleeTheRoom.view.puzzles.tiles.TilesPuzzleView;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

public class TestPuzzleView {
    @Test
    public void TestTileView() {
        Tile tile = new Tile(5,5,3,2);
        tile.addElem("ola");
        tile.addElem("meu");

        TileView view = new TileView(tile);
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        tile.setActive(false);
        view.draw(screen);

        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString("#0000FF"));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));

        tile.setActive(true);
        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        view.draw(screen);

        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString("#00FF00"));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString("#FF0000"));

        for(int i = 0; i < 2; ++i) {
            Mockito.verify(textGraphicsMock, Mockito.times(1))
                    .putString(tile.getPosition().getX(), tile.getPosition().getY() + i, tile.getLine(i));
        }
    }

    @Test
    public void TestColorSeqView() throws IOException {

        ColorSeqPuzzle puzzle = new ColorSeqPuzzle(5,5,0);
        puzzle.generateSequence();
        puzzle.addGuess("#FF0000");
        ColorSeqPuzzle puzzleSpy = Mockito.spy(puzzle);
        ColorSeqPuzzleView viewSpy = Mockito.spy(new ColorSeqPuzzleView(puzzleSpy));


        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        puzzleSpy.setState(ColorSeqPuzzle.STATE.GUESS);
        viewSpy.draw(screen);

        Mockito.verify(viewSpy, Mockito.times(1)).drawOptions(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawGuess(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(ColorSeqPuzzle.STATE.INFO);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawInfo(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(ColorSeqPuzzle.STATE.RIGHTGUESS);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawCorrectMessage(screen);
    }

    @Test
    public void TestFindSeqView() {
        FindSeqPuzzle puzzle = new FindSeqPuzzle(40,15,0);
        puzzle.randomizeSeq();
        puzzle.randomizeSeqPos();

        FindSeqPuzzle puzzleSpy = Mockito.spy(puzzle);
        FindSeqPuzzleView viewSpy = Mockito.spy(new FindSeqPuzzleView(puzzleSpy));

        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        puzzleSpy.setState(FindSeqPuzzle.STATE.INFO);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawInfo(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(FindSeqPuzzle.STATE.FINDING);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawSequences(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(FindSeqPuzzle.STATE.FOUND);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawCorrectMessage(screen);

    }

    @Test
    public void TestTilesView() {
        Tile tile1 = new Tile(0,0,2,2);
        Tile tile2 = new Tile(2,0,2,2);
        Tile tile3 = new Tile(0,2,2,2);
        Tile tile4 = new Tile(2,2,2,2);
        Tile[] tiles = {tile1, tile2, tile3, tile4};
        TilesPuzzle puzzle = new TilesPuzzle(6,6,4, 4, 0, tiles);

        TilesPuzzle puzzleSpy = Mockito.spy(puzzle);
        TilesPuzzleView viewSpy = Mockito.spy(new TilesPuzzleView(puzzleSpy));

        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        puzzleSpy.setState(TilesPuzzle.STATE.INFO);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawBackground(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawInfo(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.reset(viewSpy);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(TilesPuzzle.STATE.PUZZLE);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawBackground(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawTiles(screen);

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.reset(viewSpy);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);
        puzzleSpy.setState(TilesPuzzle.STATE.PUZZLECOMP);
        viewSpy.draw(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawBackground(screen);
        Mockito.verify(viewSpy, Mockito.times(1)).drawCorrectMessage(screen);

    }
}
