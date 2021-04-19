package com.fleeTheRoom.models.puzzles.findSeq;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.Puzzle;

import java.util.Random;

public class FindSeqPuzzle extends Puzzle {
    private STATE state;
    private String sequence;
    private Position seqPos;
    private Position currentSequence;
    int[][] numbers;
    private final int sequenceLength = 6;

    private final int seqsWidth;
    private final int seqsHeight;

    public enum STATE{
        INACTIVE,
        INFO,
        FINDING,
        FOUND,
        COMPLETE
    }

    public FindSeqPuzzle(int width, int height, int difficulty) {
        super(width, height, difficulty);
        this.state = STATE.INACTIVE;
        this.currentSequence = new Position(0,0);
        this.seqsWidth = (width - 2) - ((width-2)/2 - 1);
        this.seqsHeight = (height - 4) - ((height-4)/2 - 1);
        this.numbers = new int[seqsHeight][seqsWidth];
    }

    private boolean canMoveSeq(Position pos) {
        if(pos.getX() < 0 || pos.getX() + sequence.length() - 3 >= this.seqsWidth)
            return false;
        return pos.getY() >= 0 && pos.getY() < this.seqsHeight;
    }

    public void moveSelectionTo(Position newPos) {
        if(this.canMoveSeq(newPos)) {
            this.currentSequence = newPos;
        }
    }

    public void randomizeSeqPos() {
        int[] moves = {-2, 0, 2};
        int movX, movY;

        Random rand = new Random();

        if(seqPos == null) {
            int limit = this.seqsWidth - this.sequenceLength;
            int seqX = rand.nextInt(limit + 1);
            if(seqX % 2 != 0) {
                if(seqX == limit)
                    seqX--;
                else
                    seqX++;
            }
            this.seqPos = new Position(seqX, rand.nextInt(this.seqsHeight));
        }
        else {
            if (seqPos.getX() == 0) {
                movX = moves[rand.nextInt(2) + 1];
            } else if (seqPos.getX() == seqsWidth - sequenceLength) {
                movX = moves[rand.nextInt(2)];
            } else {
                movX = moves[rand.nextInt(3)];
            }

            if (seqPos.getY() == 0) {
                movY = moves[rand.nextInt(2) + 1]/2;
            } else if (seqPos.getY() == seqsHeight - 1) {
                movY = moves[rand.nextInt(2)]/2;
            } else {
                movY = moves[rand.nextInt(3)]/2;
            }

            this.seqPos = new Position(seqPos.getX() + movX, seqPos.getY() + movY);
        }
        for(int i = 0; i < seqsHeight; ++i) {
            for(int j = 0; j < seqsWidth; ++j) {
                numbers[i][j] = rand.nextInt(10);
            }
        }
        int aux = 0;
        for(int i = 0; i < sequence.length(); ++i) {
            if(!(i == 2 || i == 5)) {
                numbers[seqPos.getY()][seqPos.getX() + aux] = Character.getNumericValue(sequence.charAt(i));
                aux++;
            }
        }
    }

    public void randomizeSeq() {
        Random rand = new Random();
        int seq = rand.nextInt(1000000);
        StringBuilder seqAux = new StringBuilder(String.valueOf(seq));
        while(seqAux.length() < sequenceLength) {
            seqAux.insert(0, "0");
        }
        seqAux = seqAux.insert(2, " ");
        seqAux = seqAux.insert(5, " ");
        this.sequence = seqAux.toString();
    }

    public void checkSeqPos() {
        if(currentSequence.equals(seqPos))
            state = STATE.FOUND;
    }

    public STATE getState() { return this.state; }

    public Position getCurrentSequence() {
        return currentSequence;
    }

    public void setState(STATE state) {
        this.state = state;
    }

    public Position getSeqPos() {
        return seqPos;
    }

    public String getSequence() {
        return sequence;
    }

    public int[][] getNumbers() {
        return numbers;
    }


    public int getSeqsWidth() {
        return seqsWidth;
    }

    public int getSeqsHeight() {
        return seqsHeight;
    }
}
