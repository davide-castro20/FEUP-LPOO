package com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;

public class AdvanceInfoCommand implements Command {

    private final Puzzle puzzle;

    public AdvanceInfoCommand(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    @Override
    public void execute() {
        if(((ColorSeqPuzzle)puzzle).getState() == ColorSeqPuzzle.STATE.INFO)
            ((ColorSeqPuzzle)puzzle).setState(ColorSeqPuzzle.STATE.SEQUENCE);
        else if(((ColorSeqPuzzle)puzzle).getState() == ColorSeqPuzzle.STATE.RIGHTGUESS)
            ((ColorSeqPuzzle)puzzle).setState(ColorSeqPuzzle.STATE.COMPLETE);

    }
}

