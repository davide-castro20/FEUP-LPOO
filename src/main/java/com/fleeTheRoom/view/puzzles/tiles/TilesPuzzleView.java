package com.fleeTheRoom.view.puzzles.tiles;

import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleView;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import java.util.ArrayList;
import java.util.List;


public class TilesPuzzleView implements PuzzleView {
    private final String background_color = "#FFFFFF";
    private List<TileView> tileViews;
    private TilesPuzzle puzzle;

    public TilesPuzzleView(TilesPuzzle puzzle) {
        this.puzzle = puzzle;
        tileViews = new ArrayList<>();
        for(Tile tile : puzzle.getTiles()) {
            tileViews.add(new TileView(tile));
        }
    }

    public void drawBackground(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(this.background_color));
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                new TerminalSize(puzzle.getWidth(), puzzle.getHeight()),
                ' ');
    }

    private void drawFrame(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.drawRectangle(new TerminalPosition(4,4),
                new TerminalSize(puzzle.getPictureWidth()+2, puzzle.getPictureHeight()+2),
                ' ');
    }

    @Override
    public void draw(TerminalScreen screen) {

        drawBackground(screen);

        if(puzzle.getState() == TilesPuzzle.STATE.INFO) {
            drawInfo(screen);
        }
        else if(puzzle.getState() == TilesPuzzle.STATE.PUZZLE) {
            drawTiles(screen);
        }
        else if(puzzle.getState() == TilesPuzzle.STATE.PUZZLECOMP) {
            drawCorrectMessage(screen);
        }
    }

    public void drawTiles(TerminalScreen screen) {
        drawFrame(screen);
        TileView active = null;
        for(TileView tileView : tileViews) {
            if(tileView.getActive())
                active = tileView;
            else
                tileView.draw(screen);
        }
        if(active != null)
            active.draw(screen);
    }

    public void drawInfo(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(puzzle.getWidth()/2 - 5, puzzle.getHeight()/4, "Tile Puzzle");
        graphics.putString(puzzle.getWidth()/2 - 17, puzzle.getHeight()/4 + 1, "Move the pieces to the right places");
        graphics.putString(puzzle.getWidth()/2 - 11, puzzle.getHeight()/4 + 3, "Press Enter to continue");
    }

    public void drawCorrectMessage(TerminalScreen screen) {
        drawTiles(screen);
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.putString(puzzle.getWidth()/2 - 14, puzzle.getHeight()/2, "Correct! Press enter to leave");
    }

    public int getPuzzleWidth() { return this.puzzle.getWidth(); }

    public int getPuzzleHeight() { return this.puzzle.getHeight(); }
}
