package com.fleeTheRoom.view.puzzles;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.view.TerminalScreenFactory;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;

public class PuzzleGUI {

    private PuzzleView puzzleView;
    private TerminalScreen screen;
    private PuzzleInputHandler inputHandler;


    public PuzzleGUI(PuzzleView puzzleView, PuzzleInputHandler inputHandler, int fontSize) throws IOException {
        this.puzzleView = puzzleView;

        this.screen = TerminalScreenFactory.generateScreen(new TerminalSize(puzzleView.getPuzzleWidth(), puzzleView.getPuzzleHeight()), fontSize);

        this.inputHandler = inputHandler;
    }

    public void draw() throws IOException {
        //screen.clear();

        this.puzzleView.draw(screen);

        screen.refresh();
    }

    public Command getNextCommand() throws IOException {
        return inputHandler.getNextCommand(screen);
    }

    public void close() {
        try {
            screen.close();
        } catch (Exception e) {
            /* Nothing can be done */
        }
    }

}