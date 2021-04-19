package com.fleeTheRoom.models.level;

import com.fleeTheRoom.models.Position;

public abstract class Entity {
    private Position pos;

    public Entity(int x, int y) {
        this.pos = new Position(x, y);

    }

    public Position getPosition() {
        return pos;
    }

    public void setPosition(Position position) { this.pos = position; }

    public boolean isNear(Entity entity) {
        return pos.down().equals(entity.getPosition()) || pos.up().equals(entity.getPosition()) ||
                pos.left().equals(entity.getPosition()) || pos.right().equals(entity.getPosition());
    }
}
