package com.fleeTheRoom.view.Instructions;


import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.DoNothingCommand;
import com.fleeTheRoom.controllers.commands.ExitInstructionsCommand;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class InstructionsInputHandler {

    InstructionsGUI gui;

    public InstructionsInputHandler(InstructionsGUI gui){
        this.gui = gui;
    }

    public Command getNextCommand(TerminalScreen screen) throws IOException {
        KeyStroke input = screen.readInput();

        if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'q') return new ExitInstructionsCommand(gui);

        return new DoNothingCommand();
    }
}
