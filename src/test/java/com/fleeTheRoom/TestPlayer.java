package com.fleeTheRoom;

import com.fleeTheRoom.models.level.Key;
import com.fleeTheRoom.models.level.Player;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPlayer {

    @Test
    public void TestAddKey(){
        Player p = new Player(10,10);
        p.addKey(new Key(5,5,Key.Color.color1));
        assertEquals(p.getPickedKeys().size(),1);
    }

    @Test
    public void TestUseKey(){
        Player p = new Player(10,10);
        p.addKey(new Key(5,5,Key.Color.color1));
        p.useKey(Key.Color.color1);
        assertEquals(p.getPickedKeys().size(),0);
        p.addKey(new Key(5,5,Key.Color.color2));
        p.useKey(Key.Color.color1);
        assertEquals(p.getPickedKeys().size(),1);
    }


}
