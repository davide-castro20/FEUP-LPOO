package com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;

public class RedPuzzleCommand implements Command {

    private final Puzzle puzzle;

    public RedPuzzleCommand(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    @Override
    public void execute() {
        ((ColorSeqPuzzle)puzzle).addGuess("#FF0000");
    }
}
