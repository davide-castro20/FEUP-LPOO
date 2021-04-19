package com.fleeTheRoom.models.level;

public abstract class Interactable extends Entity {
    public Interactable(int x, int y) {
        super(x, y);
    }

    public abstract void interact();
}
