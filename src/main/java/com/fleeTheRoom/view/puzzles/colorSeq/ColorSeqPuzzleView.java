package com.fleeTheRoom.view.puzzles.colorSeq;

import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleView;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ColorSeqPuzzleView implements PuzzleView {

    private final ColorSeqPuzzle puzzle;
    private final String background_color = "#FFFFFF";

    public ColorSeqPuzzleView(ColorSeqPuzzle puzzle) {
        this.puzzle = puzzle;
    }

    private void drawBackground(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(this.background_color));
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                new TerminalSize(puzzle.getWidth(), puzzle.getHeight()),
                ' ');
    }


    public void draw(TerminalScreen screen) throws IOException {
        drawBackground(screen);

        if (puzzle.getState() == ColorSeqPuzzle.STATE.SEQUENCE) {
            for (int i = 0; i < puzzle.getSequence().size(); i++){
                drawBackground(screen);
                TextGraphics graphics = screen.newTextGraphics();
                graphics.setBackgroundColor(TextColor.Factory.fromString(puzzle.getSequence().get(i)));
                graphics.fillRectangle(
                        new TerminalPosition(puzzle.getWidth()/4, puzzle.getHeight()/4),
                        new TerminalSize(puzzle.getWidth()/2, puzzle.getHeight()/2),
                        ' ');


                screen.refresh();
                //Draw color
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //Erase color
                drawBackground(screen);
                screen.refresh();
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } else if (puzzle.getState() == ColorSeqPuzzle.STATE.GUESS) {
            drawOptions(screen);
            drawGuess(screen);
        } else if (puzzle.getState() == ColorSeqPuzzle.STATE.INFO){
            drawInfo(screen);
        } else if (puzzle.getState() == ColorSeqPuzzle.STATE.RIGHTGUESS){
            drawGuess(screen);
            drawCorrectMessage(screen);
        }
    }

    public void drawCorrectMessage(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.putString(puzzle.getWidth()/6, puzzle.getHeight()*4/6, "Correct! Press enter to leave");
    }


    public void drawOptions(TerminalScreen screen){
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFA500"));
        graphics.putString(1, getPuzzleHeight() - 1, "O - Orange");

        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(getPuzzleWidth()/2 + 1, getPuzzleHeight() - 1, "R - Red");

        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.putString(1, getPuzzleHeight() - 2, "G - Green");

        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#0000FF"));
        graphics.putString(getPuzzleWidth()/2 + 1, getPuzzleHeight() - 2, "B - Blue");
    }

    public void drawGuess(TerminalScreen screen){

        drawGuessRectangle(screen);

        for(int i = 0; i < puzzle.getGuess().size(); i++){
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setBackgroundColor(TextColor.Factory.fromString(puzzle.getGuess().get(i)));
            graphics.fillRectangle(
                    new TerminalPosition(puzzle.getWidth()/6 + 1 + i * (puzzle.getWidth()*4/6/puzzle.getSequenceLength()), puzzle.getHeight()/6 + 1),
                    new TerminalSize(puzzle.getWidth()*4/6/puzzle.getSequenceLength(), puzzle.getHeight()*4/6 - 2),
                    ' ');
        }
    }

    public void drawGuessRectangle(TerminalScreen screen){

        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(puzzle.getWidth()/6, puzzle.getHeight()/6 - 1, "Guess:");

        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.fillRectangle(
                new TerminalPosition(puzzle.getWidth()/6, puzzle.getHeight()/6),
                new TerminalSize(puzzle.getWidth()*4/6, puzzle.getHeight()*4/6),
                ' ');

        graphics.setBackgroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.fillRectangle(
                new TerminalPosition(puzzle.getWidth()/6 + 1, puzzle.getHeight()/6 + 1),
                new TerminalSize(puzzle.getWidth()*4/6 - 2, puzzle.getHeight()*4/6 - 2),
                ' ');

    }

    public void drawInfo(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(puzzle.getWidth()/5, puzzle.getHeight()/4, "Color sequence. Guess to pass");

        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(puzzle.getWidth()/5, puzzle.getHeight()/4 + 2, "Press Enter to continue");
    }

    public int getPuzzleWidth() { return this.puzzle.getWidth(); }

    public int getPuzzleHeight() { return this.puzzle.getHeight(); }

}
