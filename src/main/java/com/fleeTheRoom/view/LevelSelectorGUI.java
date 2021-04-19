package com.fleeTheRoom.view;

import com.fleeTheRoom.models.level.Level;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class LevelSelectorGUI {

    private BasicWindow window;
    private Screen screen;
    private final List<Map.Entry<Level,Boolean>> levels;
    private int selectedIndex;

    public LevelSelectorGUI(List<Map.Entry<Level,Boolean>> levels) throws IOException{
        this.selectedIndex = -1;
        this.levels = levels;
        initGUI();
        screen.readInput();
    }

    public void initGUI() throws IOException {
        // Setup terminal and screen layers
        Terminal terminal = new DefaultTerminalFactory().setInitialTerminalSize(new TerminalSize(23, levels.size() + 7)).createTerminal();
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        // Create panel to hold components
        Panel panel = new Panel();
        panel.setLayoutManager(new GridLayout(1));

        panel.addComponent(new Button("Instructions", () -> {
            // Actions go here
            selectedIndex = -3;
            close();
        }));

        for (int i = 0; i < levels.size();i++){

            int index = i;
            if(levels.get(i).getValue()){
                panel.addComponent(new Button("Level " + (i+1) + "  \u2713", () -> {
                    // Actions go here
                    selectedIndex = index;
                    close();

                }));
            } else {
                panel.addComponent(new Button("Level " + (i+1) + "  \u2718", () -> {
                    // Actions go here
                    selectedIndex = index;
                    close();

                }));
            }
        }


        panel.addComponent(new Button("Reset Progress", () -> {
            // Actions go here
            selectedIndex = -2;
            close();
        }));

        panel.addComponent(new Button("Exit Game", this::close));

        // Create window to hold the panel
        window = new BasicWindow();
        window.setComponent(panel);

        // Create gui and start gui
        MultiWindowTextGUI gui = new MultiWindowTextGUI(screen, new DefaultWindowManager(), new EmptySpace(TextColor.ANSI.BLUE));
        gui.addWindowAndWait(window);
    }

    public void close(){
        window.close();
        try{
            screen.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }
}
