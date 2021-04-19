package com.fleeTheRoom.models.level;

import com.fleeTheRoom.models.Position;

public abstract class Slider extends Entity {
    public Slider(int x, int y) {
        super(x, y);
    }

    public abstract Position getMovedPosition(Position playerPos);
}
