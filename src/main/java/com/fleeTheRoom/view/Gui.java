package com.fleeTheRoom.view;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.view.level.LevelView;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Gui {

    private final LevelView levelView;
    private final TerminalScreen screen;
    private final InputHandler inputHandler;
    private final InfoGUI info;


    public Gui(LevelView levelView, InputHandler inputHandler, InfoGUI info) throws IOException {
        this.levelView = levelView;
        this.info = info;
        this.screen = TerminalScreenFactory.generateScreen(new TerminalSize(levelView.getLevelWidth(), levelView.getLevelHeight() + info.getHeight()), 20);
        this.inputHandler = inputHandler;
    }

    //Maybe change because of different levels
    public static void loadCustomFont(String fontFilename){
        try {
            GraphicsEnvironment ge =
                    GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File(fontFilename)));
        } catch (IOException|FontFormatException e) {
            //Handle exception
            e.printStackTrace();
        }
    }

    public void draw() throws IOException {
        screen.clear();

        this.levelView.draw(screen);
        this.info.draw(screen);

        screen.refresh();
    }

    public Command getNextCommand() throws IOException{
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
