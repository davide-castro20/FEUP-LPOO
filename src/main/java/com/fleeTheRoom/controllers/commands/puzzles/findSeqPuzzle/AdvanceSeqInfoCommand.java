package com.fleeTheRoom.controllers.commands.puzzles.findSeqPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;

public class AdvanceSeqInfoCommand implements Command {
    private final FindSeqPuzzle puzzle;

    public AdvanceSeqInfoCommand(FindSeqPuzzle puzzle) { this.puzzle = puzzle; }
    @Override
    public void execute() {
        if(puzzle.getState() == FindSeqPuzzle.STATE.INFO)
            puzzle.setState(FindSeqPuzzle.STATE.FINDING);
        else if(puzzle.getState() == FindSeqPuzzle.STATE.FOUND)
            puzzle.setState(FindSeqPuzzle.STATE.COMPLETE);
    }
}
