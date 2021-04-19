package com.fleeTheRoom.controllers.commands.puzzles.findSeqPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;

public class MoveSeqRightCommand implements Command {
    private final FindSeqPuzzle puzzle;

    public MoveSeqRightCommand(FindSeqPuzzle findSeqPuzzle) { this.puzzle = findSeqPuzzle; }

    @Override
    public void execute() {
        if(puzzle.getState() == FindSeqPuzzle.STATE.FINDING)
            puzzle.moveSelectionTo(puzzle.getCurrentSequence().right().right());
    }
}
