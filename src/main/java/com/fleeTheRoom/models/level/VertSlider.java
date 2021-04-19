package com.fleeTheRoom.models.level;

import com.fleeTheRoom.models.Position;

public class VertSlider extends Slider {
    public VertSlider(int x, int y) {
        super(x, y);
    }

    @Override
    public Position getMovedPosition(Position playerPos) {
        if(playerPos.equals(this.getPosition().up())) {
            return this.getPosition().down();
        }
        else if(playerPos.equals(this.getPosition().down())) {
            return this.getPosition().up();
        }
        return this.getPosition();
    }
}
