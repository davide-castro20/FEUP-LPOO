package com.fleeTheRoom;

import com.fleeTheRoom.models.level.Button;
import com.fleeTheRoom.models.level.Key;
import com.fleeTheRoom.models.level.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestButton {

    @Test
    public void TestInteract(){
        Button b = new Button(5, 5, 5);
        b.interact();
        assertEquals(b.getState(), Button.STATE.PRESSED);
    }
}
