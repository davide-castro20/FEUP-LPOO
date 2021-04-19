package com.fleeTheRoom.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.swing.AWTTerminalFontConfiguration;
import com.googlecode.lanterna.terminal.swing.SwingTerminalFontConfiguration;

import java.awt.*;
import java.io.IOException;

public class TerminalScreenFactory {

    public static TerminalScreen generateScreen(TerminalSize terminalSize, int fontSize) throws IOException {
        AWTTerminalFontConfiguration cfg = new SwingTerminalFontConfiguration(
                true,
                AWTTerminalFontConfiguration.BoldMode.NOTHING,
                new Font("Unifont", Font.PLAIN,fontSize));


        DefaultTerminalFactory terminalFactory = new DefaultTerminalFactory()
                .setInitialTerminalSize(terminalSize).setTerminalEmulatorFontConfiguration(cfg);

        Terminal terminal = terminalFactory.createTerminal();
        TerminalScreen screen = new TerminalScreen(terminal);

        screen.setCursorPosition(null);   // we don't need a cursor yet
        screen.startScreen();             // screens must be started
        screen.doResizeIfNecessary();

        return screen;
    }

}
