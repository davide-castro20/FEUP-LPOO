package com.fleeTheRoom.view.puzzles;

import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public interface PuzzleView {

    int getPuzzleWidth();

    int getPuzzleHeight();

    void draw(TerminalScreen screen) throws IOException;
}
