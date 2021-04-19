package com.fleeTheRoom.controllers.commands.level;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.level.Level;
import com.fleeTheRoom.models.Position;

public class MovePlayerDownCommand implements Command {
    private final Level level;

    public MovePlayerDownCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute() {
        Position position = level.getPlayerPos().down();
        level.movePlayerTo(position);
    }
}
