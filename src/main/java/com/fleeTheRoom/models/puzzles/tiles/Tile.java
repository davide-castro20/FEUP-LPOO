package com.fleeTheRoom.models.puzzles.tiles;


import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.Entity;


public class Tile extends Entity {
    private final String[] tileElements;
    private final int width;
    private final int height;

    private final Position initPos;
    private boolean active;

    private boolean empty;

    int yElem;

    public Tile(int x, int y, int width, int height) {
        super(x, y);
        initPos = new Position(x, y);
        yElem = 0;
        this.width = width;
        this.height = height;
        tileElements = new String[height];
        this.active = false;
        this.empty = false;
    }

    public void addElem(String s) {
        tileElements[yElem] = s;
        yElem++;
    }

    public void checkEmpty() {
        for(String elem : tileElements) {
            for(int i = 0; i < elem.length(); ++i) {
                if(elem.charAt(i) != ' ') {
                    this.empty = false;
                    return;
                }
            }
        }
        this.empty = true;
    }

    public boolean checkPos() {
        return this.getPosition().equals(this.initPos);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getLine(int n) {
        return tileElements[n];
    }

    public void setActive(boolean b) {
        this.active = b;
    }

    public boolean isActive() {
        return active;
    }

    public boolean isEmpty() {
        return empty;
    }

    public Position getInitPos() {
        return initPos;
    }

    public void setEmpty(boolean empty) { this.empty = empty; }
}
