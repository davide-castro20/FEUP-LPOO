package com.fleeTheRoom.view.level;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.Key;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

public class KeyDoorView {

    private String character;
    private String color1;
    private String color2;

    public KeyDoorView(String character, String color1, String color2){
        this.character = character;
        this.color1 = color1;
        this.color2 = color2;
    }

    public void draw(TerminalScreen screen, Position position, String background_color, Key.Color color) {

        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));

        switch (color){
            case color1:
                graphics.setForegroundColor(TextColor.Factory.fromString(color1));
                break;
            case color2:
                graphics.setForegroundColor(TextColor.Factory.fromString(color2));
                break;
        }

        graphics.putString(position.getX(), position.getY(), character);
    }
}
