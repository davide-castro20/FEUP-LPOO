package com.fleeTheRoom.models.level;

public class Button extends Interactable {
    private final int number;

    public enum STATE {
        NOTPRESSED,
        PRESSED
    }

    private STATE state;

    public Button(int x, int y, int number) {
        super(x, y);
        this.number = number;
        this.state = STATE.NOTPRESSED;
    }

    public int getNumber() {
        return number;
    }

    public STATE getState() {
        return state;
    }

    @Override
    public void interact() {
        state = STATE.PRESSED;
    }

    public void setState(STATE state) { this.state = state; }
}
