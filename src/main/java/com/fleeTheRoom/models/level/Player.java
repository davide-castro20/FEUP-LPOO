package com.fleeTheRoom.models.level;

import java.util.ArrayList;
import java.util.List;

public class Player extends Entity {
    private final List<Key> pickedKeys;

    public Player(int x, int y) {
        super(x, y);
        this.pickedKeys = new ArrayList<>();
    }

    public List<Key> getPickedKeys() {
        return pickedKeys;
    }

    public List<Key> getPickedKeysByColor(Key.Color color) {
        List<Key> result = new ArrayList<>();

        for(Key k : pickedKeys){
            if(k.getColor() == color){
                result.add(k);
            }
        }

        return result;
    }

    public void addKey(Key k) {
        pickedKeys.add(k);
    }

    public void useKey(Key.Color color) {
        for(Key k : pickedKeys){
            if(k.getColor() == color){
                pickedKeys.remove(k);
                break;
            }
        }
    }
}
