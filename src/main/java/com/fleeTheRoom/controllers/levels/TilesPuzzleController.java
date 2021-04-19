package com.fleeTheRoom.controllers.levels;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleGUI;
import com.fleeTheRoom.view.puzzles.tiles.TilesInputHandler;
import com.fleeTheRoom.view.puzzles.tiles.TilesPuzzleView;

import java.io.IOException;

public class TilesPuzzleController {
    private TilesPuzzle tilesPuzzle;
    private PuzzleGUI gui;

    public TilesPuzzleController(TilesPuzzle tilesPuzzle) { this.tilesPuzzle = tilesPuzzle; }

    public void start() throws IOException {
        initPuzzle();
        TilesInputHandler puzzleInputHandler = new TilesInputHandler(tilesPuzzle);
        TilesPuzzleView puzzleView = new TilesPuzzleView(tilesPuzzle);
        gui = new PuzzleGUI(puzzleView, puzzleInputHandler, 20);

        while(tilesPuzzle.getState() != TilesPuzzle.STATE.COMPLETE && tilesPuzzle.isActive()) {
            update(tilesPuzzle.getState(), gui);
        }

        this.tilesPuzzle.setActive(false);
        gui.close();
    }

    public void initPuzzle() {
        tilesPuzzle.setActive(true);
        tilesPuzzle.setState(TilesPuzzle.STATE.INFO);
    }

    public void update(TilesPuzzle.STATE state, PuzzleGUI gui) throws IOException {
        gui.draw();
        Command command = gui.getNextCommand();
        command.execute();

        if(state == TilesPuzzle.STATE.PUZZLE)
            tilesPuzzle.checkPositions();
    }
}
