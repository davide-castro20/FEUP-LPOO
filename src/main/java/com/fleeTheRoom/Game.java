package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.creators.LevelCreator;
import com.fleeTheRoom.view.*;
import com.fleeTheRoom.models.level.Level;
import com.fleeTheRoom.models.level.LevelObserver;
import com.fleeTheRoom.view.Instructions.InstructionsGUI;
import com.fleeTheRoom.view.level.LevelView;

import javax.sound.sampled.LineUnavailableException;
import java.io.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Game implements LevelObserver {
    private List<Map.Entry<Level,Boolean>>levels; /* boolean Used to know if level has already been cleared*/
    private Gui gui;

    public static void main(String[] args) throws IOException, LineUnavailableException {
        new Game().start();
    }

    private void start() throws IOException, LineUnavailableException {

        String levelsPath = "src\\main\\resources\\levels\\level";
        String savePath = "src\\main\\resources\\levels\\progress.txt";
        loadLevels(savePath, levelsPath);
        String fontFilename = "src\\main\\resources\\unifont-13.0.01.ttf";
        Gui.loadCustomFont(fontFilename);

        while(true){
            LevelSelectorGUI selectorGUI = new LevelSelectorGUI(levels);

            if(selectorGUI.getSelectedIndex() == -1){
                saveProgress(savePath);
                break;
            } else if(selectorGUI.getSelectedIndex() == -2) { //Reset Progress
                resetProgress();
                continue;
            } else if(selectorGUI.getSelectedIndex() == -3) {
                InstructionsGUI gui = new InstructionsGUI();
                gui.draw();
                continue;
            }

            Level currentLevel;
            if(levels.get(selectorGUI.getSelectedIndex()).getValue() || levels.get(selectorGUI.getSelectedIndex()).getKey().isFinished()){ //Played before
                currentLevel = loadSpecificLevel(levelsPath,selectorGUI.getSelectedIndex() + 1);
            } else {
                currentLevel = levels.get(selectorGUI.getSelectedIndex()).getKey();
            }

            String graphicsPath = "src\\main\\resources\\levels\\graphics.txt";
            LevelView levelView = new LevelView(currentLevel, graphicsPath);
            Audio gameAudio = new Audio();
            InputHandler inputHandler = new InputHandler(currentLevel, gameAudio);
            InfoGUI info = new InfoGUI(1,currentLevel,levelView);

            gui = new Gui(levelView,inputHandler,info);
            gui.draw();

            gameAudio.start();
            while (!currentLevel.isNextLevel()) {
                Command command = gui.getNextCommand();
                command.execute();
            }
            gameAudio.close();
            currentLevel.dontNextLevel();

            if(currentLevel.isFinished())
                levels.set(selectorGUI.getSelectedIndex(), new AbstractMap.SimpleEntry<>(currentLevel, Boolean.TRUE));

            gui.close();
        }
    }

    @Override
    public void levelChanged() {
        try{
            gui.draw();
        } catch(Exception e){
            /* Nothing can be done */
        }
    }

    public void loadLevels(String save, String level_path){ //Load all levels
        this.levels = new ArrayList<>();

        List<Map.Entry<String, String>> progress = new ArrayList<>();

        BufferedReader fileReader = null;
        try {
            fileReader = new BufferedReader(new FileReader(save));
            String line;
            while ((line = fileReader.readLine()) != null) {
                progress.add(new AbstractMap.SimpleEntry<>(line.split(",")[0], line.split(",")[1]));
            }

            for (int i = 0; i < progress.size(); i++){
                LevelCreator creator = new LevelCreator(level_path + progress.get(i).getKey() + ".txt");
                if(progress.get(i).getValue().equals("0"))
                    levels.add(new AbstractMap.SimpleEntry<>(creator.createLevel(), Boolean.FALSE));
                else
                    levels.add(new AbstractMap.SimpleEntry<>(creator.createLevel(), Boolean.TRUE));
                levels.get(i).getKey().addObserver(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileReader != null;
                fileReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Level loadSpecificLevel(String path, int number){
        LevelCreator creator = new LevelCreator(path + number + ".txt");
        Level result = creator.createLevel();
        result.addObserver(this);
        return result;
    }

    public void resetProgress() {
        for(int i = 0; i < levels.size(); ++i) {
            levels.add(new AbstractMap.SimpleEntry<>(levels.get(0).getKey(), Boolean.FALSE));
            levels.remove(0);
            levels.get(0).getKey().finish();
        }
    }

    public void saveProgress(String progressPath) {
        BufferedWriter fileWriter = null;
        try {
            fileWriter = new BufferedWriter(new FileWriter(progressPath));
            for (Map.Entry<Level, Boolean> level : levels) {
                if (level.getValue().equals(Boolean.TRUE)) {
                    fileWriter.write(level.getKey().getId() + ",1\n");
                } else {
                    fileWriter.write(level.getKey().getId() + ",0\n");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fileWriter != null;
                fileWriter.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public List<Map.Entry<Level, Boolean>> getLevels() {
        return this.levels;
    }
}
