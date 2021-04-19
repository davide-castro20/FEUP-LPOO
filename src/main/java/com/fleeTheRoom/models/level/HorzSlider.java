package com.fleeTheRoom.models.level;

import com.fleeTheRoom.models.Position;

public class HorzSlider extends Slider {
    public HorzSlider(int x, int y) {
        super(x, y);
    }

    @Override
    public Position getMovedPosition(Position playerPos) {
        if(playerPos.equals(this.getPosition().left())) {
            return this.getPosition().right();
        }
        else if(playerPos.equals(this.getPosition().right())) {
            return this.getPosition().left();
        }
        return this.getPosition();
    }
}
