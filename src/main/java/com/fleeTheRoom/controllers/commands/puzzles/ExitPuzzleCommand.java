package com.fleeTheRoom.controllers.commands.puzzles;


import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.Puzzle;

public class ExitPuzzleCommand implements Command {
    private final Puzzle puzzle;

    public ExitPuzzleCommand(Puzzle puzzle) {
        this.puzzle = puzzle;
    }

    @Override
    public void execute() {
        puzzle.setActive(false);
    }
}
