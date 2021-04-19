package com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

public class SwitchTileCommand implements Command {
    private final TilesPuzzle tilesPuzzle;

    public SwitchTileCommand(TilesPuzzle tilesPuzzle) {
        this.tilesPuzzle = tilesPuzzle;
    }

    @Override
    public void execute() {
        if(tilesPuzzle.getState() == TilesPuzzle.STATE.PUZZLE)
            this.tilesPuzzle.switchActive();
    }
}
