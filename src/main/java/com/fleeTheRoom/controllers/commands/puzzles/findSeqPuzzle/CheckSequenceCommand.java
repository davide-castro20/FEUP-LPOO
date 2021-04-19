package com.fleeTheRoom.controllers.commands.puzzles.findSeqPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;

public class CheckSequenceCommand implements Command {
    private final FindSeqPuzzle puzzle;

    public CheckSequenceCommand(FindSeqPuzzle puzzle) { this.puzzle = puzzle; }

    @Override
    public void execute() {
        puzzle.checkSeqPos();
    }
}
