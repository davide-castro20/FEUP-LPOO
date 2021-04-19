package com.fleeTheRoom;
import static org.junit.Assert.*;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.*;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqButton;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqButton;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzleButton;
import com.fleeTheRoom.view.level.*;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.screen.TerminalScreen;
import org.junit.Test;
import org.mockito.Mockito;


public class TestLevelView {
    @Test
    public void TestButtonDoorView() {
        ButtonDoorView view = new ButtonDoorView("d","#FFFFFF");
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, new Position(1,1), "#FF0000");

        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString("#FF0000"));

        Mockito.verify(textGraphicsMock, Mockito.times(1)).setForegroundColor(TextColor.Factory.fromString("#FFFFFF"));
        Mockito.verify(textGraphicsMock, Mockito.times(1)).putString(1, 1, "d");
    }

    @Test
    public void TestButtonView() {
        String colorCleared = "#00FF00";
        String colorNotCleared = "#FF0000";
        String background = "#FFFFFF";
        String character = "d";
        Position pos = new Position(1,1);

        ButtonView view = new ButtonView(character, colorCleared, colorNotCleared);
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, PuzzleButton.STATE.NOTCLEARED);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(colorNotCleared));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, PuzzleButton.STATE.CLEARED);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(colorCleared));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, Button.STATE.NOTPRESSED);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(colorNotCleared));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, Button.STATE.PRESSED);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(colorCleared));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();
    }

    @Test
    public void TestEntityView() {
        String character = "e";
        String color = "#F0F0F0";
        String background = "#FFFFFF";
        Position pos = new Position(1,2);

        EntityView view = new EntityView(character, color);
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(color));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();
    }

    @Test
    public void TestKeyDoorView() {
        String character = "d";
        String color1 = "#0000FF";
        String color2 = "#0000F0";
        String background = "#FFFFFF";
        Position pos = new Position(2,2);

        KeyDoorView view = new KeyDoorView(character, color1, color2);
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, Key.Color.color1);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(color1));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();

        Mockito.reset(textGraphicsMock);
        Mockito.reset(screen);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        view.draw(screen, pos, background, Key.Color.color2);
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setBackgroundColor(TextColor.Factory.fromString(background));
        Mockito.verify(textGraphicsMock,Mockito.times(1))
                .setForegroundColor(TextColor.Factory.fromString(color2));
        Mockito.verify(textGraphicsMock, Mockito.times(1))
                .putString(pos.getX(), pos.getY(), character);
        Mockito.verify(screen, Mockito.times(1)).newTextGraphics();
    }

    @Test
    public void TestLevelView() {
        Level level = new Level(10,10,0,1);

        Player player = new Player(1,1);
        Objective obj = new Objective(2,1);
        Wall wall1 = new Wall(4,1);
        Wall wall2 = new Wall(5,1);
        Key key1 = new Key(6,5, Key.Color.color2);
        Key key2 = new Key(7,5, Key.Color.color2);
        Key key3 = new Key(6,7, Key.Color.color2);
        Door door1 = new Door(5,5, Key.Color.color1);
        Slider slider1 = new VertSlider(7,6);
        Slider slider2 = new HorzSlider(9,9);
        Door door2 = new Door(8,8, null);
        Button button1 = new Button(9,8,1);
        ColorSeqButton colorButton = new ColorSeqButton(7,7,0, PuzzleButton.STATE.NOTCLEARED);
        TilesPuzzleButton tileButton = new TilesPuzzleButton(3,3,0, PuzzleButton.STATE.NOTCLEARED);
        FindSeqButton findSeqButton = new FindSeqButton(2,2,0, PuzzleButton.STATE.NOTCLEARED);

        level.addPlayer(player);
        level.addObjective(obj);
        level.addWall(wall1);
        level.addWall(wall2);
        level.addKey(key1);
        level.addKey(key2);
        level.addKey(key3);
        level.addDoor(door1);
        level.addSlider(slider1);
        level.setButtonDoor(door2);
        level.addInteractable(button1);
        level.addSlider(slider2);
        level.addInteractable(colorButton);
        level.addInteractable(tileButton);
        level.addInteractable(findSeqButton);

        LevelView view = new LevelView(level, "src\\main\\resources\\levels\\graphicsTest.txt");
        TerminalScreen screen = Mockito.mock(TerminalScreen.class);
        TextGraphics textGraphicsMock = Mockito.mock(TextGraphics.class);
        Mockito.when(screen.newTextGraphics()).thenReturn(textGraphicsMock);

        EntityView playerView = Mockito.mock(EntityView.class);
        EntityView objectiveView = Mockito.mock(EntityView.class);
        EntityView wallView = Mockito.mock(EntityView.class);
        KeyDoorView keyView = Mockito.mock(KeyDoorView.class);
        KeyDoorView doorView = Mockito.mock(KeyDoorView.class);
        EntityView vertSliderView = Mockito.mock(EntityView.class);
        ButtonDoorView buttonDoorView = Mockito.mock(ButtonDoorView.class);
        ButtonView buttonView = Mockito.mock(ButtonView.class);
        EntityView horzSliderView = Mockito.mock(EntityView.class);
        ButtonView colorButtonView = Mockito.mock(ButtonView.class);
        ButtonView tileButtonView = Mockito.mock(ButtonView.class);
        ButtonView findSeqButtonView = Mockito.mock(ButtonView.class);

        view.setPlayerView(playerView);
        view.setObjectiveView(objectiveView);
        view.setWallView(wallView);
        view.setKeyView(keyView);
        view.setDoorView(doorView);
        view.setVertSliderView(vertSliderView);
        view.setButtonDoorView(buttonDoorView);
        view.setButtonView(buttonView);
        view.setHorzSliderView(horzSliderView);
        view.setColorSeqButtonView(colorButtonView);
        view.setTilePuzzleButtonView(tileButtonView);
        view.setFindPuzzleButtonView(findSeqButtonView);

        view.draw(screen);

        Mockito.verify(playerView, Mockito.times(1))
                .draw(screen, player.getPosition(), view.getBackground_color());
        Mockito.verify(objectiveView, Mockito.times(1))
                .draw(screen, obj.getPosition(), view.getBackground_color());
        Mockito.verify(wallView, Mockito.times(1))
                .draw(screen, wall1.getPosition(), view.getBackground_color());
        Mockito.verify(wallView, Mockito.times(1))
                .draw(screen, wall2.getPosition(), view.getBackground_color());
        Mockito.verify(keyView, Mockito.times(1))
                .draw(screen, key1.getPosition(), view.getBackground_color(), key1.getColor());
        Mockito.verify(keyView, Mockito.times(1))
                .draw(screen, key2.getPosition(), view.getBackground_color(), key2.getColor());
        Mockito.verify(keyView, Mockito.times(1))
                .draw(screen, key3.getPosition(), view.getBackground_color(), key3.getColor());
        Mockito.verify(doorView, Mockito.times(1))
                .draw(screen, door1.getPosition(), view.getBackground_color(), door1.getColor());
        Mockito.verify(vertSliderView, Mockito.times(1))
                .draw(screen, slider1.getPosition(), view.getBackground_color());
        Mockito.verify(buttonDoorView, Mockito.times(1))
                .draw(screen, door2.getPosition(), view.getBackground_color());
        Mockito.verify(buttonView, Mockito.times(1))
                .draw(screen, button1.getPosition(), view.getBackground_color(), button1.getState());
        Mockito.verify(horzSliderView, Mockito.times(1))
                .draw(screen, slider2.getPosition(), view.getBackground_color());
        Mockito.verify(colorButtonView, Mockito.times(1))
                .draw(screen, colorButton.getPosition(), view.getBackground_color(), colorButton.getState());
        Mockito.verify(tileButtonView, Mockito.times(1))
                .draw(screen, tileButton.getPosition(), view.getBackground_color(), tileButton.getState());
        Mockito.verify(findSeqButtonView, Mockito.times(1))
                .draw(screen, findSeqButton.getPosition(), view.getBackground_color(), findSeqButton.getState());

    }
}
