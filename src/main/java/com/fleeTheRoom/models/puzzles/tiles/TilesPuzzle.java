package com.fleeTheRoom.models.puzzles.tiles;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TilesPuzzle extends Puzzle {

    private STATE state;

    private Tile[] tiles;

    private int activeTile;

    int pictureWidth;
    int pictureHeight;

    public enum STATE{
        INACTIVE,
        INFO,
        PUZZLE,
        PUZZLECOMP,
        COMPLETE
    }

    public TilesPuzzle(int width, int height, int pictureWidth, int pictureHeight, int difficulty, Tile[] tiles) {
        super(width, height, difficulty);
        this.state = STATE.INACTIVE;
        this.activeTile = 0;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;
        this.tiles = tiles;
        this.tiles[activeTile].setActive(true);
        randomizeTiles();
    }

    public void checkPositions() {
        List<Position> emptyPos = new ArrayList<>();
        for(Tile tile: tiles) {
            if(tile.isEmpty()) {
                emptyPos.add(tile.getInitPos());
            }
        }

        for(Tile tile : tiles) {
            if(!tile.isEmpty() && !tile.checkPos())
                return;
            else if(tile.isEmpty()) {
                if(emptyPos.contains(tile.getPosition())) {
                    emptyPos.remove(tile.getPosition());
                } else return;
            }
        }

        this.tiles[activeTile].setActive(false);
        this.setState(STATE.PUZZLECOMP);
    }


    public void moveTileTo(Position newPosition) {
        if(canMoveTile(newPosition)) {
            tiles[activeTile].setPosition(newPosition);
        }
    }

    public boolean canMoveTile(Position position) {
        Tile tile = tiles[activeTile];
        if (position.getX() < 0 || position.getX() + tile.getWidth() >= this.getWidth()) return false;
        return position.getY() >= 0 && position.getY() + tile.getHeight() < this.getHeight();
    }

    public void switchActive() {
        this.tiles[activeTile].setActive(false);
        this.activeTile = (activeTile+1) % tiles.length;
        this.tiles[activeTile].setActive(true);
    }

    private void randomizeTiles() {
        Random rand = new Random();
        for(Tile tile : tiles) {
            tile.setPosition(new Position(rand.nextInt(this.getWidth()-tile.getWidth()),
                    rand.nextInt(this.getHeight() - tile.getHeight())));
        }
    }

    public Tile getActiveTile() {
        return tiles[activeTile];
    }

    public STATE getState() {
        return this.state;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getPictureWidth() {
        return pictureWidth;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public void setState(TilesPuzzle.STATE state) {
        this.state = state;
    }

    public void setTiles(Tile[] tiles) { this.tiles = tiles; }
}
