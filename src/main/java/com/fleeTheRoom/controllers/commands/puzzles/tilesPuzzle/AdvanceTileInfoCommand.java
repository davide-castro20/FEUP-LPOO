package com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

public class AdvanceTileInfoCommand implements Command {

    private final Puzzle puzzle;

    public AdvanceTileInfoCommand(Puzzle puzzle){
        this.puzzle = puzzle;
    }

    @Override
    public void execute() {
        TilesPuzzle.STATE state = ((TilesPuzzle)puzzle).getState();
        if(state == TilesPuzzle.STATE.INFO)
            ((TilesPuzzle)puzzle).setState(TilesPuzzle.STATE.PUZZLE);
        else if(state == TilesPuzzle.STATE.PUZZLECOMP)
            ((TilesPuzzle)puzzle).setState(TilesPuzzle.STATE.COMPLETE);
    }
}

