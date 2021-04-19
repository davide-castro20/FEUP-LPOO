package com.fleeTheRoom.view.level;

import com.fleeTheRoom.models.Position;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

public class EntityView {

    private String character;
    private String color;

    public EntityView(String character, String color){
        this.character = character;
        this.color = color;
    }

    public void draw(TerminalScreen screen, Position position, String background_color) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(background_color));
        graphics.setForegroundColor(TextColor.Factory.fromString(color));

        graphics.putString(position.getX(), position.getY(), character);
    }
}
