package com.fleeTheRoom;

import com.fleeTheRoom.models.Position;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPosition {

    @Test
    public void testConstructor(){
        Position pos = new Position(10,10);
        assertEquals(pos.getX(),10);
        assertEquals(pos.getY(),10);
    }

    @Test
    public void testDirections(){
        int x = 10,y = 10;
        Position pos = new Position(x,y);
        assertEquals(pos.down(), new Position(x,y + 1));
        assertEquals(pos.left(), new Position(x - 1,y));
        assertEquals(pos.up(), new Position(x,y - 1));
        assertEquals(pos.right(), new Position(x + 1,y));
    }
}
