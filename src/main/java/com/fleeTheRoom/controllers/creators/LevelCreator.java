package com.fleeTheRoom.controllers.creators;

import com.fleeTheRoom.models.level.*;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqButton;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqButton;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzleButton;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class LevelCreator {

    private final String filename;

    public LevelCreator(String filename) {this.filename = filename; }

    public Level createLevel() {

        BufferedReader fileReader = null;

        int height,width;
        int difficulty;
        Level level = null;
        Map<String,String> characters = new HashMap<>();

        final String DELIMITER = ",";
        final String SEPARATOR = "::::";
        try {
            String line;
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(filename));


            boolean reached_separator = false;

            int y = 0;

            line = fileReader.readLine();
            String[] tokens = line.split(DELIMITER);
            height = Integer.parseInt(tokens[0]);
            width = Integer.parseInt(tokens[1]);
            line = fileReader.readLine();
            difficulty = Integer.parseInt(line);
            level = new Level(width, height, difficulty,
                    Character.getNumericValue(filename.charAt(filename.length() - 5)));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {
                if(!reached_separator){
                    tokens = line.split(DELIMITER);
                    if(tokens[0].equals(SEPARATOR)) {
                        reached_separator = true;
                    } else {
                        characters.put(tokens[0],tokens[1]);
                    }
                } else {
                    for(int x = 0;x < width;x++){
                        if(isElement(characters,"player",line.charAt(x))){
                            level.addPlayer(new Player(x, y));
                        } else if(isElement(characters,"wall",line.charAt(x))){
                            level.addWall(new Wall(x, y));
                        } else if(isElement(characters,"objective",line.charAt(x))) {
                            level.addObjective(new Objective(x, y));
                        }else if(isElement(characters,"key1",line.charAt(x))) {
                            level.addKey(new Key(x, y, Key.Color.color1));
                        }else if(isElement(characters,"key2",line.charAt(x))) {
                            level.addKey(new Key(x, y, Key.Color.color2));
                        } else if(isElement(characters,"door1",line.charAt(x))) {
                            level.addDoor(new Door(x, y, Key.Color.color1));
                        } else if(isElement(characters,"door2",line.charAt(x))) {
                            level.addDoor(new Door(x, y, Key.Color.color2));
                        } else if(isElement(characters,"horzSlider",line.charAt(x))) {
                            level.addSlider(new HorzSlider(x, y));
                        } else if(isElement(characters,"vertSlider",line.charAt(x))) {
                            level.addSlider(new VertSlider(x, y));
                        } else if(isElement(characters,"colorseqbutton",line.charAt(x))) {
                            level.addInteractable(new ColorSeqButton(x, y, difficulty, PuzzleButton.STATE.NOTCLEARED));
                        } else if(isElement(characters,"tilepuzzlebutton",line.charAt(x))) {
                            level.addInteractable(new TilesPuzzleButton(x, y, difficulty,PuzzleButton.STATE.NOTCLEARED));
                        } else if(isElement(characters,"findseqbutton",line.charAt(x))) {
                            level.addInteractable(new FindSeqButton(x, y, difficulty,PuzzleButton.STATE.NOTCLEARED));
                        } else if(Character.isDigit(line.charAt(x))) {
                            level.addInteractable(new Button(x, y, Character.getNumericValue(line.charAt(x))));
                        } else if(isElement(characters,"buttondoor",line.charAt(x))) {
                            level.setButtonDoor(new Door(x, y, null));
                        }
                    }

                    y++;
                }
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


        return level;
    }

    private boolean isElement(Map<String,String> characters, String element, char character){
        return characters.get(element) != null && characters.get(element).equals(String.valueOf(character));
    }

}



