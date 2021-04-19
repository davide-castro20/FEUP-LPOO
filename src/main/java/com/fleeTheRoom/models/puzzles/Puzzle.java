package com.fleeTheRoom.models.puzzles;

public abstract class Puzzle {

    private int width;
    private int height;

    private int difficulty;

    private boolean isActive;

    public Puzzle(int width, int height, int difficulty) {
        this.width = width;
        this.height = height;
        this.isActive = false;
        this.difficulty = difficulty;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() { return height; }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public int getDifficulty() {
        return difficulty;
    }
}
