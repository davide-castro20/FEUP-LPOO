package com.fleeTheRoom.controllers.levels;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqPuzzle;
import com.fleeTheRoom.view.puzzles.PuzzleGUI;
import com.fleeTheRoom.view.puzzles.findSeq.FindSeqInputHandler;
import com.fleeTheRoom.view.puzzles.findSeq.FindSeqPuzzleView;

import java.io.IOException;

public class FindSeqPuzzleController {
    private final FindSeqPuzzle findSeqPuzzle;

    public FindSeqPuzzleController(FindSeqPuzzle findSeqPuzzle) {
        this.findSeqPuzzle = findSeqPuzzle;
    }

    public void start() throws IOException {
        this.findSeqPuzzle.setActive(true);
        this.findSeqPuzzle.setState(FindSeqPuzzle.STATE.INFO);
        FindSeqPuzzleView puzzleView = new FindSeqPuzzleView(findSeqPuzzle);
        FindSeqInputHandler inputHandler = new FindSeqInputHandler(findSeqPuzzle);
        PuzzleGUI gui = new PuzzleGUI(puzzleView, inputHandler, 48);

        findSeqPuzzle.randomizeSeq();
        findSeqPuzzle.randomizeSeqPos();

        int timeBetweenChange = 1500 * (2 - findSeqPuzzle.getDifficulty());

        long startFrame = System.currentTimeMillis();
        long startChange = startFrame;

        while((findSeqPuzzle.getState() != FindSeqPuzzle.STATE.COMPLETE) && findSeqPuzzle.isActive()) {
            long currentTime = System.currentTimeMillis();
            long currentChange = System.currentTimeMillis();
            if(currentTime > startFrame + 20) {
                if(findSeqPuzzle.getState() == FindSeqPuzzle.STATE.FINDING) {
                    if (currentChange > startChange + timeBetweenChange) {
                        startChange = currentChange;
                        findSeqPuzzle.randomizeSeqPos();
                    }
                }
                gui.draw();
                Command command = gui.getNextCommand();
                command.execute();
            }
        }

        this.findSeqPuzzle.setActive(false);
        gui.close();
    }

}
