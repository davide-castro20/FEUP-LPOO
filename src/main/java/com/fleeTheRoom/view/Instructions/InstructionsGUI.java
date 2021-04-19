package com.fleeTheRoom.view.Instructions;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.view.TerminalScreenFactory;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.IOException;

public class InstructionsGUI {

    private final int WIDTH = 60;
    private final int HEIGHT = 24;
    private final String BACKGROUND_COLOR = "#FFFFFF";
    private boolean active;

    private final String[] INSTRUCTIONS = {"LEVEL:",
                                        "\tMove with Arrow Keys",
                                        "\tInteract with Puzzle Buttons or 'H' Buttons with E key",
                                        "\tPress all 'H' buttons to open a door \u233B",
                                        "\tPick up keys \u26B7 to open corresponding doors \u2360",
                                        "\tQuit Level with Q key",
                                        "\tReach the objective \u004F", "",
                                        "COLOR SEQUENCE PUZZLE ( \u0042 ):",
                                        "\tQuit puzzle with Q key", "",
                                        "TILE PUZZLE ( \u0054 ):",
                                        "\tChange tile piece with E key",
                                        "\tQuit puzzle with Q key", "",
                                        "NUMBER SEQUENCE PUZZLE ( \u0046 ):",
                                        "\tMove selection with Arrow keys",
                                        "\tSubmit sequence with E key", "",
                                        "EXIT THIS SCREEN WITH Q KEY"};



    private final TerminalScreen screen;
    private final InstructionsInputHandler inputHandler;

    public InstructionsGUI() throws IOException {

        screen = TerminalScreenFactory.generateScreen(new TerminalSize(WIDTH, HEIGHT), 20);
        this.inputHandler = new InstructionsInputHandler(this);
        this.active = false;
    }

    public void draw() throws IOException {
        this.active = true;
        screen.clear();
        drawBackground(screen);
        drawInstructions(screen);

        Command cmd;
        screen.refresh();

        while(this.active) {
            cmd = inputHandler.getNextCommand(screen);
            cmd.execute();
        }
    }

    public Command getNextCommand() throws IOException {
        return inputHandler.getNextCommand(screen);
    }

    public void close() {
        try {
            screen.close();
            this.active = false;
        } catch (Exception e) {
            /* Nothing can be done */
        }
    }


    private void drawBackground(TerminalScreen screen) {
            TextGraphics graphics = screen.newTextGraphics();
            graphics.setBackgroundColor(TextColor.Factory.fromString(BACKGROUND_COLOR));
            graphics.fillRectangle(
                    new TerminalPosition(0, 0),
                    new TerminalSize(WIDTH, HEIGHT),
                    ' ');
    }

    private void drawInstructions(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(BACKGROUND_COLOR));
        graphics.setForegroundColor(TextColor.Factory.fromString("#FF0000"));
        graphics.putString(WIDTH/3, 0, "INSTRUCTIONS");

        for (int i = 0; i < INSTRUCTIONS.length; i++){
            TextGraphics graphics1 = screen.newTextGraphics();
            graphics1.setBackgroundColor(TextColor.Factory.fromString(BACKGROUND_COLOR));
            String FOREGROUND_COLOR = "#000000";
            graphics1.setForegroundColor(TextColor.Factory.fromString(FOREGROUND_COLOR));
            graphics1.putString(0, 2 + i, INSTRUCTIONS[i]);
        }
        
    }

}