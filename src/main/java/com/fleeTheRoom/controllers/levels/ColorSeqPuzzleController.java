package com.fleeTheRoom.controllers.levels;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleGUI;
import com.fleeTheRoom.view.puzzles.colorSeq.ColorSeqInputHandler;
import com.fleeTheRoom.view.puzzles.colorSeq.ColorSeqPuzzleView;

import java.io.IOException;

public class ColorSeqPuzzleController {
    private final ColorSeqPuzzle colorSeqPuzzle;

    public ColorSeqPuzzleController(ColorSeqPuzzle colorSeqPuzzle) { this.colorSeqPuzzle = colorSeqPuzzle; }

    public void start() throws IOException {
        initPuzzle();
        ColorSeqInputHandler puzzleInputHandler = new ColorSeqInputHandler(this.colorSeqPuzzle);
        ColorSeqPuzzleView puzzleView = new ColorSeqPuzzleView(this.colorSeqPuzzle);
        PuzzleGUI gui = new PuzzleGUI(puzzleView, puzzleInputHandler, 20);

        while(colorSeqPuzzle.getState() != ColorSeqPuzzle.STATE.COMPLETE && colorSeqPuzzle.isActive()) {
            update(colorSeqPuzzle.getState(), gui);
        }

        this.colorSeqPuzzle.setActive(false);
        gui.close();
    }

    public void initPuzzle() {
        colorSeqPuzzle.initGuess();
        colorSeqPuzzle.setState(ColorSeqPuzzle.STATE.INFO);
        this.colorSeqPuzzle.setActive(true);
        colorSeqPuzzle.generateSequence();
    }

    public void update(ColorSeqPuzzle.STATE state, PuzzleGUI gui) throws IOException {
        if(state == ColorSeqPuzzle.STATE.SEQUENCE) {
            gui.draw();
            colorSeqPuzzle.setState(ColorSeqPuzzle.STATE.GUESS);
        } else {
            gui.draw();
            Command command = gui.getNextCommand();
            command.execute();

            if (colorSeqPuzzle.getGuess().size() == colorSeqPuzzle.getSequenceLength() &&
                    colorSeqPuzzle.getState() == ColorSeqPuzzle.STATE.GUESS) {
                colorSeqPuzzle.checkSequence();
            }
        }
    }
}
