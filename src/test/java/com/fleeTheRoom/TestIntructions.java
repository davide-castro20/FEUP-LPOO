package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.ExitInstructionsCommand;
import com.fleeTheRoom.view.Instructions.InstructionsGUI;
import com.fleeTheRoom.view.Instructions.InstructionsInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Test;
import org.mockito.Mockito;
import java.io.IOException;

import static org.junit.Assert.*;

public class TestIntructions {
    @Test
    public void TestInstructionsInputHandler() throws IOException {
        InstructionsGUI gui = Mockito.mock(InstructionsGUI.class);
        InstructionsInputHandler inputHandler = new InstructionsInputHandler(gui);

        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke('q',false, false));
        Command cmd = inputHandler.getNextCommand(screen);
        assertTrue(cmd instanceof ExitInstructionsCommand);
        cmd.execute();
        Mockito.verify(gui, Mockito.times(1)).close();

    }
}
