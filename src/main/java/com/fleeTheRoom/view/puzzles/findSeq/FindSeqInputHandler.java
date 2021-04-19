package com.fleeTheRoom.view.puzzles.findSeq;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.DoNothingCommand;
import com.fleeTheRoom.controllers.commands.puzzles.ExitPuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.findSeqPuzzle.*;
import com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle.*;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class FindSeqInputHandler extends PuzzleInputHandler {

    public FindSeqInputHandler(Puzzle puzzle) {
        super(puzzle);
    }

    @Override
    public Command getNextCommand(TerminalScreen screen) throws IOException {
        KeyStroke input = screen.pollInput();
        FindSeqPuzzle findSeqPuzzle = (FindSeqPuzzle) puzzle;

        if(input != null) {
            if (input.getKeyType() == KeyType.EOF) return new ExitPuzzleCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'q')
                return new ExitPuzzleCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.ArrowDown) return new MoveSeqDownCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.ArrowUp) return new MoveSeqUpCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.ArrowLeft) return new MoveSeqLeftCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.ArrowRight) return new MoveSeqRightCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'e')
                return new CheckSequenceCommand(findSeqPuzzle);
            if (input.getKeyType() == KeyType.Enter) return new AdvanceSeqInfoCommand(findSeqPuzzle);
        }
        return new DoNothingCommand();
    }
}
