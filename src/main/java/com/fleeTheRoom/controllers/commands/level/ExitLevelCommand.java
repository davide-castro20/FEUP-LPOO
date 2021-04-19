package com.fleeTheRoom.controllers.commands.level;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.level.Level;

public class ExitLevelCommand implements Command {
    private final Level level;

    public ExitLevelCommand(Level level) {
        this.level = level;
    }
    @Override
    public void execute() {
        this.level.goNextLevel();
    }
}
