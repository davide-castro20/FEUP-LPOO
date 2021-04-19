package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.puzzles.ExitPuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.findSeqPuzzle.*;
import com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle.*;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;

import com.fleeTheRoom.view.puzzles.PuzzleInputHandler;
import com.fleeTheRoom.view.puzzles.findSeq.FindSeqInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static org.junit.Assert.*;

public class TestFindSeqPuzzleCommands {
    private FindSeqPuzzle findSeqPuzzle;
    private PuzzleInputHandler inputHandler;
    @Before
    public void initTest() {
        this.findSeqPuzzle = new FindSeqPuzzle(40, 15, 0);
        findSeqPuzzle.randomizeSeq();
        findSeqPuzzle.randomizeSeqPos();
        inputHandler = new FindSeqInputHandler(findSeqPuzzle);
    }

    @Test
    public void TestAdvanceSeqInfoCommand() throws IOException {
        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);

        findSeqPuzzle.setState(FindSeqPuzzle.STATE.INFO);
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.Enter));
        Command cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof AdvanceSeqInfoCommand);
        cmd.execute();
        assertEquals(findSeqPuzzle.getState(), FindSeqPuzzle.STATE.FINDING);

        findSeqPuzzle.setState(FindSeqPuzzle.STATE.FOUND);
        cmd.execute();
        assertEquals(findSeqPuzzle.getState(), FindSeqPuzzle.STATE.COMPLETE);
    }


    @Test
    public void TestMoveSeqCommands() throws IOException {
        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);

        findSeqPuzzle.setState(FindSeqPuzzle.STATE.INFO);

        findSeqPuzzle.moveSelectionTo(new Position(1,1));
        Position oldPos = findSeqPuzzle.getCurrentSequence();

        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        Command cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqDownCommand);
        cmd.execute();
        assertEquals(findSeqPuzzle.getState(), FindSeqPuzzle.STATE.INFO);
        assertEquals(oldPos, findSeqPuzzle.getCurrentSequence());


        findSeqPuzzle.setState(FindSeqPuzzle.STATE.FINDING);

        findSeqPuzzle.moveSelectionTo(new Position(1,1));

        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowDown));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqDownCommand);
        cmd.execute();
        assertEquals(new Position(1,2), findSeqPuzzle.getCurrentSequence());


        findSeqPuzzle.moveSelectionTo(new Position(1,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqRightCommand);
        cmd.execute();
        assertEquals(new Position(3,1), findSeqPuzzle.getCurrentSequence());

        findSeqPuzzle.moveSelectionTo(new Position(2,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqLeftCommand);
        cmd.execute();
        assertEquals(new Position(0,1), findSeqPuzzle.getCurrentSequence());

        findSeqPuzzle.moveSelectionTo(new Position(1,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqUpCommand);
        cmd.execute();
        assertEquals(new Position(1,0), findSeqPuzzle.getCurrentSequence());

        findSeqPuzzle.moveSelectionTo(new Position(1,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowLeft));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqLeftCommand);
        cmd.execute();
        assertEquals(new Position(1,1), findSeqPuzzle.getCurrentSequence());

        findSeqPuzzle.moveSelectionTo(new Position(1,0));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowUp));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqUpCommand);
        cmd.execute();
        assertEquals(new Position(1,0), findSeqPuzzle.getCurrentSequence());

        findSeqPuzzle.moveSelectionTo(new Position(14,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke(KeyType.ArrowRight));
        cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof MoveSeqRightCommand);
        cmd.execute();
        assertEquals(new Position(14,1), findSeqPuzzle.getCurrentSequence());
    }

    @Test
    public void TestRandomizeSeqPos() {
        Position oldPos = findSeqPuzzle.getCurrentSequence();
        findSeqPuzzle.randomizeSeqPos();
        Position newPos = findSeqPuzzle.getCurrentSequence();

        assertTrue(newPos.getX() >= oldPos.getX() - 2);
        assertTrue(newPos.getX() <= oldPos.getX() + 2);
        assertTrue(newPos.getY() >= oldPos.getY() - 1);
        assertTrue(newPos.getY() <= oldPos.getY() + 1);
    }

    @Test
    public void TestCheckSequenceCommand() throws IOException {
        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);

        findSeqPuzzle.setState(FindSeqPuzzle.STATE.FINDING);

        if(findSeqPuzzle.getSeqPos().equals(findSeqPuzzle.getCurrentSequence()))
            findSeqPuzzle.moveSelectionTo(new Position(0,1));
        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke('e', false, false));
        Command cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof CheckSequenceCommand);
        cmd.execute();
        assertEquals(findSeqPuzzle.getState(), FindSeqPuzzle.STATE.FINDING);

        findSeqPuzzle.moveSelectionTo(findSeqPuzzle.getSeqPos());
        cmd.execute();
        assertEquals(findSeqPuzzle.getState(), FindSeqPuzzle.STATE.FOUND);
    }

    @Test
    public void TestExitPuzzle() throws IOException {
        findSeqPuzzle.setActive(true);
        assertTrue(findSeqPuzzle.isActive());

        TerminalScreen screenMock = Mockito.mock(TerminalScreen.class);

        Mockito.when(screenMock.pollInput()).thenReturn(new KeyStroke('q', false, false));
        Command cmd = inputHandler.getNextCommand(screenMock);
        assertTrue(cmd instanceof ExitPuzzleCommand);
        cmd.execute();

        assertFalse(findSeqPuzzle.isActive());
    }

}
