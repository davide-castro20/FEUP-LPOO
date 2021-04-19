package com.fleeTheRoom.view.puzzles.colorSeq;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.DoNothingCommand;
import com.fleeTheRoom.controllers.commands.puzzles.ExitPuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.colorSeqPuzzle.*;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class ColorSeqInputHandler extends PuzzleInputHandler {
    public ColorSeqInputHandler(Puzzle puzzle) {
        super(puzzle);
    }

    @Override
    public Command getNextCommand(TerminalScreen screen) throws IOException {

        KeyStroke input = screen.readInput();

        if(((ColorSeqPuzzle)puzzle).getState() == ColorSeqPuzzle.STATE.RIGHTGUESS){
            if(input.getKeyType() == KeyType.Enter) return new AdvanceInfoCommand(puzzle);
        } else {
            if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'q') return new ExitPuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'o') return new OrangePuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'r') return new RedPuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'g') return new GreenPuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'b') return new BluePuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'x') return new RestartGuessPuzzleCommand(puzzle);
            else if(input.getKeyType() == KeyType.Enter) return new AdvanceInfoCommand(puzzle);
        }
        return new DoNothingCommand();
    }
}
