package com.fleeTheRoom.view.puzzles.tiles;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.DoNothingCommand;
import com.fleeTheRoom.controllers.commands.puzzles.ExitPuzzleCommand;
import com.fleeTheRoom.controllers.commands.puzzles.tilesPuzzle.*;
import com.fleeTheRoom.models.puzzles.Puzzle;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleInputHandler;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class TilesInputHandler extends PuzzleInputHandler {
    public TilesInputHandler(Puzzle puzzle) {
        super(puzzle);
    }

    @Override
    public Command getNextCommand(TerminalScreen screen) throws IOException {
        KeyStroke input = screen.readInput();
        TilesPuzzle tilesPuzzle = (TilesPuzzle)puzzle;

        if (input.getKeyType() == KeyType.EOF) return new ExitPuzzleCommand(tilesPuzzle);
        if(input.getKeyType() == KeyType.Character && input.getCharacter() == 'q') return new ExitPuzzleCommand(tilesPuzzle);
        if (input.getKeyType() == KeyType.ArrowDown) return new MoveTileDownCommand(tilesPuzzle);
        if (input.getKeyType() == KeyType.ArrowUp) return new MoveTileUpCommand(tilesPuzzle);
        if (input.getKeyType() == KeyType.ArrowLeft) return new MoveTileLeftCommand(tilesPuzzle);
        if (input.getKeyType() == KeyType.ArrowRight) return new MoveTileRightCommand(tilesPuzzle);
        if (input.getKeyType() == KeyType.Character && input.getCharacter() == 'e') return new SwitchTileCommand(tilesPuzzle);
        if(input.getKeyType() == KeyType.Enter) return new AdvanceTileInfoCommand(puzzle);
        return new DoNothingCommand();
    }
}
