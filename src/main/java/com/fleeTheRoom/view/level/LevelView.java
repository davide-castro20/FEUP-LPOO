package com.fleeTheRoom.view.level;

import com.fleeTheRoom.models.level.*;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqButton;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqButton;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzleButton;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;

import java.io.BufferedReader;
import java.io.FileReader;

public class LevelView {

    private EntityView playerView;
    private EntityView objectiveView;
    private EntityView wallView;

    private KeyDoorView keyView;
    private KeyDoorView doorView;
    private ButtonDoorView buttonDoorView;

    private EntityView horzSliderView;
    private EntityView vertSliderView;

    private String background_color;
    private ButtonView colorSeqButtonView;
    private ButtonView tilePuzzleButtonView;
    private ButtonView findPuzzleButtonView;
    private ButtonView buttonView;

    private Level level;

    public LevelView(Level level,String sourceFile) {
        this.level = level;
        readLevelViewFromFile(sourceFile);
    }

    private String numberToUnicode(String num) {
        return String.valueOf(Character.toChars(Integer.parseInt(num,16)));
    }

    private void readLevelViewFromFile(String sourceFile) {
        BufferedReader fileReader = null;

        //Delimiter used in file
        final String DELIMITER = ",";
        try {
            String line;
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(sourceFile));

            //Read the file line by line
            while ((line = fileReader.readLine()) != null) {

                String[] tokens = line.split(DELIMITER);
                switch (tokens[0]) {
                    case "background":
                        this.background_color = tokens[1];
                        break;
                    case "player":
                        this.playerView = new EntityView(numberToUnicode(tokens[2]),tokens[1]);
                        break;
                    case "wall":
                        this.wallView = new EntityView(numberToUnicode(tokens[2]),tokens[1]);
                        break;
                    case "objective":
                        this.objectiveView = new EntityView(numberToUnicode(tokens[2]),tokens[1]);
                        break;
                    case "key":
                        this.keyView = new KeyDoorView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "door":
                        this.doorView = new KeyDoorView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "horzSlider":
                        this.horzSliderView = new EntityView(numberToUnicode(tokens[2]),tokens[1]);
                        break;
                    case "vertSlider":
                        this.vertSliderView = new EntityView(numberToUnicode(tokens[2]),tokens[1]);
                        break;
                    case "colorseqbutton":
                        this.colorSeqButtonView = new ButtonView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "tilepuzzlebutton":
                        this.tilePuzzleButtonView = new ButtonView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "findseqbutton":
                        this.findPuzzleButtonView = new ButtonView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "button":
                        this.buttonView = new ButtonView(numberToUnicode(tokens[3]),tokens[1],tokens[2]);
                        break;
                    case "buttondoor":
                        this.buttonDoorView = new ButtonDoorView(numberToUnicode(tokens[2]), tokens[1]);
                        break;
                    default:
                        break;
                }
            }
        } catch(Exception e) {
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

    private void drawBackground(TerminalScreen screen) {
        TextGraphics graphics = screen.newTextGraphics();
        graphics.setBackgroundColor(TextColor.Factory.fromString(this.background_color));
        graphics.fillRectangle(
                new TerminalPosition(0, 0),
                new TerminalSize(level.getWidth(), level.getHeight()),
                ' ');
    }


    public void draw(TerminalScreen screen){
        drawBackground(screen);
        playerView.draw(screen, level.getPlayerPos(),background_color);
        objectiveView.draw(screen, level.getObjective().getPosition(),background_color);

        for(Wall w : level.getWalls())
            wallView.draw(screen, w.getPosition(),background_color);

        for(Key k : level.getKeys())
            keyView.draw(screen, k.getPosition(),background_color,k.getColor());

        for(Door d : level.getDoors())
            doorView.draw(screen, d.getPosition(),background_color,d.getColor());

        if(level.getButtonDoor() != null)
            buttonDoorView.draw(screen, level.getButtonDoor().getPosition(), background_color);

        for(Slider s : level.getSliders()) {
            if(s instanceof HorzSlider)
                horzSliderView.draw(screen, s.getPosition(), background_color);
            else
                vertSliderView.draw(screen, s.getPosition(), background_color);
        }

        for(Interactable interactable : level.getInteractables()) {
            if(interactable instanceof ColorSeqButton) {
                colorSeqButtonView.draw(screen, interactable.getPosition(), background_color,((PuzzleButton) interactable).getState());
            }
            else if(interactable instanceof TilesPuzzleButton) {
                tilePuzzleButtonView.draw(screen, interactable.getPosition(), background_color,((PuzzleButton) interactable).getState());
            }
            else if(interactable instanceof FindSeqButton) {
                findPuzzleButtonView.draw(screen, interactable.getPosition(), background_color,((PuzzleButton) interactable).getState());
            }
            else if(interactable instanceof Button) {
                if(((Button) interactable).getNumber() <= level.getButtonNumber()) {
                    buttonView.draw(screen, interactable.getPosition(), background_color, ((Button) interactable).getState());
                }
            }
        }
    }

    public int getLevelWidth(){
        return level.getWidth();
    }

    public int getLevelHeight(){
        return level.getHeight();
    }

    public KeyDoorView getKeyView(){
        return keyView;
    }

    public void setPlayerView(EntityView playerView) {
        this.playerView = playerView;
    }

    public void setObjectiveView(EntityView objectiveView) {
        this.objectiveView = objectiveView;
    }

    public void setWallView(EntityView wallView) {
        this.wallView = wallView;
    }

    public void setKeyView(KeyDoorView keyView) {
        this.keyView = keyView;
    }

    public void setDoorView(KeyDoorView doorView) {
        this.doorView = doorView;
    }

    public void setButtonDoorView(ButtonDoorView buttonDoorView) {
        this.buttonDoorView = buttonDoorView;
    }

    public void setHorzSliderView(EntityView horzSliderView) {
        this.horzSliderView = horzSliderView;
    }

    public void setVertSliderView(EntityView vertSliderView) {
        this.vertSliderView = vertSliderView;
    }

    public void setColorSeqButtonView(ButtonView colorSeqButtonView) {
        this.colorSeqButtonView = colorSeqButtonView;
    }

    public void setTilePuzzleButtonView(ButtonView tilePuzzleButtonView) {
        this.tilePuzzleButtonView = tilePuzzleButtonView;
    }

    public void setFindPuzzleButtonView(ButtonView findPuzzleButtonView) {
        this.findPuzzleButtonView = findPuzzleButtonView;
    }

    public void setButtonView(ButtonView buttonView) {
        this.buttonView = buttonView;
    }

    public String getBackground_color() {
        return background_color;
    }
}
