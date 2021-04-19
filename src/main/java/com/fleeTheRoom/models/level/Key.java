package com.fleeTheRoom.models.level;

public class Key extends Entity {

    public enum Color {
        color1,
        color2
    }

    private final Color color;

    public Key(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
