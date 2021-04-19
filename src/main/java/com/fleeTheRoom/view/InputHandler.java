package com.fleeTheRoom.view;

import com.fleeTheRoom.controllers.commands.*;
import com.fleeTheRoom.controllers.commands.level.*;
import com.fleeTheRoom.models.level.Level;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class InputHandler {

    private final Level level;
    private final Audio audio;

    public InputHandler(Level level, Audio audio){
        this.level = level;
        this.audio = audio;
    }

    public Command getNextCommand(TerminalScreen screen) throws IOException {
        KeyStroke input = screen.readInput();

        if (input.getKeyType() == KeyType.EOF) return new ExitLevelCommand(level);
        if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'q') return new ExitLevelCommand(level);
        if (input.getKeyType() == KeyType.ArrowDown) return new MovePlayerDownCommand(level);
        if (input.getKeyType() == KeyType.ArrowUp) return new MovePlayerUpCommand(level);
        if (input.getKeyType() == KeyType.ArrowLeft) return new MovePlayerLeftCommand(level);
        if (input.getKeyType() == KeyType.ArrowRight) return new MovePlayerRightCommand(level);
        if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'e') return new InteractCommand(level, audio);

        return new DoNothingCommand();
    }
}
