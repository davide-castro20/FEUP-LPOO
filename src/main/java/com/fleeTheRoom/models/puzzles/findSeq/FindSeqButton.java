package com.fleeTheRoom.models.puzzles.findSeq;

import com.fleeTheRoom.controllers.levels.FindSeqPuzzleController;
import com.fleeTheRoom.models.puzzles.PuzzleButton;

public class FindSeqButton extends PuzzleButton {
    public FindSeqButton(int x, int y, int difficulty, PuzzleButton.STATE state) {
        super(x, y, difficulty, state);
    }

    @Override
    public void interact() {
        try {
            FindSeqPuzzle findSeqPuzzle = new FindSeqPuzzle(40, 15, difficulty);
            FindSeqPuzzleController findSeqPuzzleController = new FindSeqPuzzleController(findSeqPuzzle);
            findSeqPuzzleController.start();
            if(findSeqPuzzle.getState() == FindSeqPuzzle.STATE.COMPLETE) {
                this.setState(STATE.CLEARED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
