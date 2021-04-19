package com.fleeTheRoom.view;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.Key;
import com.fleeTheRoom.models.level.Level;
import com.fleeTheRoom.view.level.KeyDoorView;
import com.fleeTheRoom.view.level.LevelView;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

public class InfoGUI {
    private final int height;
    private final Level level;
    private final LevelView levelView;
    private final int startX;
    private final int startY;


    public InfoGUI(int height, Level level, LevelView levelView){
        this.height = height;
        this.level = level;
        this.startY = level.getHeight();
        this.startX = 0;
        this.levelView = levelView;
    }

    public void drawKeyCounters(TerminalScreen screen){
        drawKeyCounter(screen, 0, Key.Color.color1);
        drawKeyCounter(screen, 4, Key.Color.color2);
    }

    public void drawKeyCounter(TerminalScreen screen, int Xoffset, Key.Color color){
        KeyDoorView keyView = levelView.getKeyView();
        //keyView.draw(screen, new Position(startX + Xoffset,startY), levelView.getBackground_color(), color);
        keyView.draw(screen, new Position(startX + Xoffset,startY), "#000000", color);

        TextGraphics graphics = screen.newTextGraphics();
        //graphics.setBackgroundColor(TextColor.Factory.fromString(levelView.getBackground_color()));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics.putString(startX + Xoffset + 1, startY, "x");

        TextGraphics graphics1 = screen.newTextGraphics();
        //graphics1.setBackgroundColor(TextColor.Factory.fromString(levelView.getBackground_color()));
        graphics1.setBackgroundColor(TextColor.Factory.fromString("#000000"));
        graphics1.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        graphics1.putString(startX + Xoffset + 2, startY, String.valueOf(level.getPlayer().getPickedKeysByColor(color).size()));
    }

    public void drawDifficulty(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        int difficulty = level.getDifficulty();
        //graphics.setBackgroundColor(TextColor.Factory.fromString(levelView.getBackground_color()));
        graphics.setBackgroundColor(TextColor.Factory.fromString("#000000"));

        switch (difficulty) {
            case 0:
                graphics.setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
                graphics.putString(level.getWidth() - 7, startY, "Medium");
                break;
            case 1:
                graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
                graphics.putString(level.getWidth() - 5, startY, "Hard");
                break;
            default:
                break;
        }
    }

    public void draw(TerminalScreen screen){
        drawKeyCounters(screen);
        drawDifficulty(screen);
    }

    public int getHeight() {
        return height;
    }
}
