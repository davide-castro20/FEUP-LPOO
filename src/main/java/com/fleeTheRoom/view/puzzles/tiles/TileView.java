package com.fleeTheRoom.view.puzzles.tiles;

import com.fleeTheRoom.models.puzzles.tiles.Tile;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

public class TileView {
    private Tile tile;

    public TileView(Tile tile) {
        this.tile = tile;
    }

    public void draw(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();

        if(!tile.isActive()) {
            graphics.setBackgroundColor(TextColor.Factory.fromString("#0000FF"));
            graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        } else {
            graphics.setBackgroundColor(TextColor.Factory.fromString("#00FF00"));
            graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        }

        for(int i = 0; i < tile.getHeight(); ++i) {
            graphics.putString(tile.getPosition().getX(), tile.getPosition().getY()+i, tile.getLine(i));
        }
    }

    public boolean getActive() {
        return tile.isActive();
    }
}
