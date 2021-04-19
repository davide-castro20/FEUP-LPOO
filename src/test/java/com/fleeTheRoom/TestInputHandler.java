package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.*;
import com.fleeTheRoom.controllers.commands.level.*;
import com.fleeTheRoom.models.level.*;
import com.fleeTheRoom.view.Audio;
import com.fleeTheRoom.view.InputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;


public class TestInputHandler {
    private TerminalScreen screen;
    private InputHandler inputHandler;
    @Before
    public void initTestInput()  {
        Level level = new Level(50, 50, 0, 0);
        screen = Mockito.mock(TerminalScreen.class);
        Audio audio = Mockito.mock(Audio.class);
        inputHandler = new InputHandler(level, audio);
    }
    @Test
    public void TestLevelGetNextCommand() throws Exception {
        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.EOF));
        Command command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof ExitLevelCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke('q',false, false));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof ExitLevelCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof MovePlayerDownCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof MovePlayerLeftCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof MovePlayerUpCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof MovePlayerRightCommand);

        Mockito.when(screen.readInput()).thenReturn(new KeyStroke('e',false, false));
        command = inputHandler.getNextCommand(screen);
        assertTrue(command instanceof InteractCommand);
    }
}
