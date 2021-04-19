package com.fleeTheRoom.models.puzzles.colorSeq;

import com.fleeTheRoom.controllers.levels.ColorSeqPuzzleController;
import com.fleeTheRoom.models.puzzles.PuzzleButton;

public class ColorSeqButton extends PuzzleButton {

    public ColorSeqButton(int x, int y, int difficulty, PuzzleButton.STATE state) {
        super(x, y, difficulty, state);
    }

    public void interact() {
        try {
            ColorSeqPuzzle puzzle = new ColorSeqPuzzle(48, 20, difficulty);
            ColorSeqPuzzleController puzzleController = new ColorSeqPuzzleController(puzzle);
            puzzleController.start();
            if(puzzle.getState() == ColorSeqPuzzle.STATE.COMPLETE){
                this.setState(STATE.CLEARED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
