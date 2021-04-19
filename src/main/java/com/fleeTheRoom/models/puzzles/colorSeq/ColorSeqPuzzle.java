package com.fleeTheRoom.models.puzzles.colorSeq;

import com.fleeTheRoom.models.puzzles.Puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


//TODO restart puzzle command
public class ColorSeqPuzzle extends Puzzle {

    private final String[] COLOR_POSSIBILITIES = {"#FFA500","#FF0000","#00FF00","#0000FF"};
    private final List<String> sequence;
    private STATE state;
    private List<String> guess;
    private final int sequenceLength;


    public enum STATE{
        INFO,
        INACTIVE,
        SEQUENCE,
        GUESS,
        RIGHTGUESS,
        COMPLETE
    }

    public ColorSeqPuzzle(int width, int height, int difficulty) {
        super(width, height, difficulty);
        this.sequence = new ArrayList<>();
        this.guess = new ArrayList<>();
        this.state = STATE.INACTIVE;
        this.sequenceLength = difficulty == 0 ? 3 : 5;
    }

    public void initGuess() { guess = new ArrayList<>(); }

    public void checkSequence() {
        if(guess.equals(sequence)) {
            state = STATE.RIGHTGUESS;
        } else {
            resetGuess();
        }
    }

    public void generateSequence(){
        Random rand = new Random();
        for (int i = 0; i < sequenceLength; i++) {
            sequence.add(COLOR_POSSIBILITIES[rand.nextInt(4)]);
        }
    }

    public STATE getState() {
        return state;
    }

    public List<String> getSequence() {
        return sequence;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public void addGuess(String guess){
        this.guess.add(guess);
    }

    public List<String> getGuess() {
        return guess;
    }

    public void resetGuess(){
        guess.clear();
    }

    public int getSequenceLength() {
        return sequenceLength;
    }

}
