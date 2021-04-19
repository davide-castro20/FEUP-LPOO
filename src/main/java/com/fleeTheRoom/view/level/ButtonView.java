package com.fleeTheRoom.view.level;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.Button;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.awt.*;

public class ButtonView {

    private String character;
    private String colorCleared;
    private String colorNotCleared;

    public ButtonView(String character, String colorCleared, String colorNotCleared){
        this.character = character;
        this.colorCleared = colorCleared;
        this.colorNotCleared = colorNotCleared;
    }

    public void draw(TerminalScreen screen, Position position, String background_color, PuzzleButton.STATE state) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));

        switch (state){
            case CLEARED:
                graphics.setForegroundColor(TextColor.Factory.fromString(colorCleared));
                break;
            case NOTCLEARED:
                graphics.setForegroundColor(TextColor.Factory.fromString(colorNotCleared));
                break;
        }
        graphics.putString(position.getX(), position.getY(), character);
    }

    public void draw(TerminalScreen screen, Position position, String background_color, Button.STATE state) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));

        switch (state){
            case PRESSED:
                graphics.setForegroundColor(TextColor.Factory.fromString(colorCleared));
                break;
            case NOTPRESSED:
                graphics.setForegroundColor(TextColor.Factory.fromString(colorNotCleared));
                break;
        }
        graphics.putString(position.getX(), position.getY(), character);
    }
}
