package com.fleeTheRoom.view.puzzles;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public abstract class PuzzleInputHandler {
    protected Puzzle puzzle;

    public PuzzleInputHandler(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    public abstract Command getNextCommand(TerminalScreen screen) throws IOException;
}
