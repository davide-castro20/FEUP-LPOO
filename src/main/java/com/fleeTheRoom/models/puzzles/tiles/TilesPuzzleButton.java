package com.fleeTheRoom.models.puzzles.tiles;

import com.fleeTheRoom.controllers.creators.TilePuzzleCreator;
import com.fleeTheRoom.controllers.levels.TilesPuzzleController;
import com.fleeTheRoom.models.puzzles.PuzzleButton;

import java.util.Random;


public class TilesPuzzleButton extends PuzzleButton {
    private final int puzzleNum;

    public TilesPuzzleButton(int x, int y, int difficulty, STATE state) {
        super(x, y, difficulty, state);
        Random rand = new Random();
        int numPuzzles = 2;
        puzzleNum = rand.nextInt(numPuzzles) + 1;
    }

    @Override
    public void interact() {
        try {
            String tilePuzzlesPath = "src\\main\\resources\\tilePuzzles\\";
            TilePuzzleCreator tilePuzzleCreator = new TilePuzzleCreator(tilePuzzlesPath +
                    "tilePuzzle" + puzzleNum + ".txt", difficulty);
            TilesPuzzle puzzle = tilePuzzleCreator.createPuzzle();

            TilesPuzzleController puzzleController = new TilesPuzzleController(puzzle);
            puzzleController.start();

            if (puzzle.getState() == TilesPuzzle.STATE.COMPLETE) {
                this.setState(STATE.CLEARED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
