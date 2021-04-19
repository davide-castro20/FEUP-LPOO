package com.fleeTheRoom.controllers.commands.level;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.level.Level;
import com.fleeTheRoom.models.Position;

public class MovePlayerLeftCommand implements Command {
    private final Level level;

    public MovePlayerLeftCommand(Level level) {
        this.level = level;
    }

    @Override
    public void execute() {
        Position position = level.getPlayerPos().left();
        level.movePlayerTo(position);
    }
}
