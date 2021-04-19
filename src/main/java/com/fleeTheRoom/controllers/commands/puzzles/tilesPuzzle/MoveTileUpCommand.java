package com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

public class MoveTileUpCommand implements Command {

    private TilesPuzzle tilesPuzzle;

    public MoveTileUpCommand(TilesPuzzle tilesPuzzle) {
        this.tilesPuzzle = tilesPuzzle;
    }

    @Override
    public void execute() {
        if(tilesPuzzle.getState() == TilesPuzzle.STATE.PUZZLE) {
            Position newPosition = tilesPuzzle.getActiveTile().getPosition().up();
            this.tilesPuzzle.moveTileTo(newPosition);
        }
    }
}