package com.fleeTheRoom.models.puzzles;

import com.fleeTheRoom.models.level.Interactable;

public abstract class PuzzleButton extends Interactable {

    private STATE state;
    protected int difficulty;

    public enum STATE{
        CLEARED,
        NOTCLEARED
    }

    public PuzzleButton(int x, int y, int difficulty, STATE state) {
        super(x, y);
        this.difficulty = difficulty;
        this.state = state;
    }

    @Override
    public abstract void interact();

    public STATE getState() {
        return state;
    }

    public void setState(STATE state) {
        this.state = state;
    }
}
