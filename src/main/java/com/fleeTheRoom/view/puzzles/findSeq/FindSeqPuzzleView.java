package com.fleeTheRoom.view.puzzles.findSeq;

import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleView;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class FindSeqPuzzleView implements PuzzleView {
    private final String background_color = "#FFFFFF";
    private FindSeqPuzzle puzzle;

    public FindSeqPuzzleView(FindSeqPuzzle puzzle) { this.puzzle = puzzle; }

    @Override
    public void draw(TerminalScreen screen) {
        if(puzzle.getState() == FindSeqPuzzle.STATE.INFO) {
            drawInfo(screen);
        }
        else if(puzzle.getState() == FindSeqPuzzle.STATE.FINDING) {
            drawSequences(screen);
        }
        else if(puzzle.getState() == FindSeqPuzzle.STATE.FOUND) {
            drawCorrectMessage(screen);
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

    public void drawInfo(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(puzzle.getWidth()/2 - 10, puzzle.getHeight()/4, "Number Sequence Puzzle");
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));
        graphics.putString(puzzle.getWidth()/4, puzzle.getHeight()/4 + 2, "Find the right sequence");
        graphics.putString(puzzle.getWidth()/2 - 10, puzzle.getHeight()/4 + 4, "Press Enter to continue");
    }

    public void drawSequences(TerminalScreen screen) {
        drawBackground(screen);
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#000000"));

        TextGraphics graphicsSel = screen.newTextGraphics();
        graphicsSel.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphicsSel.setForegroundColor(TextColor.Factory.fromString("#FF0000"));


        graphics.putString(getPuzzleWidth()/2 - puzzle.getSequence().length()/2, 2,
                puzzle.getSequence());

        for(int i = 0; i < puzzle.getSeqsHeight(); ++i) {
            int aux = 0;
            int posX = 5;
            for (int j = 0; j < puzzle.getSeqsWidth(); ++j) {
                if(aux == 2) {
                    aux = 0;
                    graphics.putString(posX, i + 4, " ");
                    j--;
                    posX++;
                }
                else {
                    aux++;
                    if(i == puzzle.getCurrentSequence().getY() && (j >= puzzle.getCurrentSequence().getX() && j < puzzle.getCurrentSequence().getX() + puzzle.getSequence().length() - 2))
                        graphicsSel.putString(posX, i + 4, String.valueOf(puzzle.getNumbers()[i][j]));
                    else
                        graphics.putString(posX, i+4, String.valueOf(puzzle.getNumbers()[i][j]));
                    posX++;
                }
            }
        }
    }

    public void drawCorrectMessage(TerminalScreen screen) {
        drawSequences(screen);
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString("#00FF00"));
        graphics.putString(puzzle.getWidth()/2 - 15, puzzle.getHeight()/2, "Correct! Press enter to leave");
    }

    @Override
    public int getPuzzleWidth() {
        return this.puzzle.getWidth();
    }

    @Override
    public int getPuzzleHeight() {
        return this.puzzle.getHeight();
    }
}
