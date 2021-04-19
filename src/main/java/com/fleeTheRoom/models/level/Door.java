package com.fleeTheRoom.models.level;

public class Door extends Entity {

    private final Key.Color color;

    public Door(int x, int y, Key.Color color) {
        super(x, y);
        this.color = color;
    }

    public Key.Color getColor() {
        return color;
    }
}
