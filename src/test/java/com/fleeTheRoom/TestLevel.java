package com.fleeTheRoom;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.controllers.commands.level.InteractCommand;
import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.level.*;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqButton;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqButton;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzleButton;
import com.fleeTheRoom.view.Audio;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestLevel {
    private Level level;
    @Before
    public void InitTestLevel() {
        level =  new Level(10, 20, 0,0);
    }
    @Test
    public void TestConstructor() {
        assertEquals(level.getWidth(), 10);
        assertEquals(level.getHeight(), 20);
        assertNotEquals(level.getDoors(), null);
        assertNotEquals(level.getKeys(), null);
        assertNotEquals(level.getWalls(), null);
        assertNull(level.getPlayer());
        assertNotEquals(level.getObservers(), null);
        assertFalse(level.isFinished());
    }
    @Test
    public void TestFinish() {
        level.finish();
        assertTrue(level.isFinished());
    }

    @Test
    public void TestAddObserver() {
        LevelObserver observerMock = Mockito.mock(LevelObserver.class);
        level.addObserver(observerMock);
        assertEquals(level.getObservers().size(), 1);
    }
    @Test
    public void TestNotifyObservers() {
        LevelObserver observerMock = Mockito.mock(LevelObserver.class);
        LevelObserver observerMock2 = Mockito.mock(LevelObserver.class);

        level.addObserver(observerMock);
        level.addObserver(observerMock);
        level.addObserver(observerMock);
        level.addObserver(observerMock2);

        level.notifyObservers();

        Mockito.verify(observerMock, Mockito.times(3)).levelChanged();
        Mockito.verify(observerMock2, Mockito.times(1)).levelChanged();
    }
    @Test
    public void TestGetAllEntities() {
        List<Entity> entities = new ArrayList<>();
        Wall wall1 = new Wall(5,5);
        Wall wall2 = new Wall(6,6);
        Door door1 = new Door(7,7,Key.Color.color1);
        Door door2 = new Door(8,8,Key.Color.color2);
        Key key = new Key(6,6,Key.Color.color1);
        Objective objective = new Objective(4,5);
        Player player = new Player(9,9);
        level.addWall(wall1); level.addWall(wall2);
        level.addDoor(door1); level.addDoor(door2);
        level.addKey(key);
        level.addPlayer(player);
        level.addObjective(objective);
        entities.add(player);
        entities.add(wall1); entities.add(wall2);
        entities.add(objective);
        entities.add(door1); entities.add(door2);
        entities.add(key);
        assertEquals(entities, level.getAllEntities());
    }
    @Test
    public void TestMovePlayerToWithoutSliders() {
        Player player = new Player(5,5);
        level.addPlayer(player);
        level.addObjective(new Objective(8,8));
        level.addKey(new Key(4,5,Key.Color.color1));
        level.addWall(new Wall(5,6));
        level.movePlayerTo(new Position(5,6));
        assertEquals(level.getPlayer().getPosition().getX(), 5);
        assertEquals(level.getPlayer().getPosition().getY(), 5);
    }
    @Test
    public void TestMovePlayerToWithSliders() {
        Player player = new Player(5,5);
        Slider slider1 = new HorzSlider(6,5);
        Slider slider2 = new HorzSlider(7,5);

        level.addPlayer(player);
        level.addObjective(new Objective(9,8));
        level.addWall(new Wall(10,5));
        level.addSlider(slider1);
        level.addSlider(slider2);
        level.movePlayerTo(new Position(6,5));
        assertEquals(level.getPlayer().getPosition().getX(), 6);
        assertEquals(level.getPlayer().getPosition().getY(), 5);
        assertEquals(slider1.getPosition().getX(), 7);
        assertEquals(slider1.getPosition().getY(), 5);
        assertEquals(slider2.getPosition().getX(), 8);
        assertEquals(slider2.getPosition().getY(), 5);

        Slider slider3 = new VertSlider(6, 6);
        Slider slider4 = new VertSlider(6,7);
        Slider slider5 = new HorzSlider(6, 9);
        level.addSlider(slider3);
        level.addSlider(slider4);
        level.addSlider(slider5);
        level.movePlayerTo(new Position(6,6));
        assertEquals(level.getPlayer().getPosition().getX(), 6);
        assertEquals(level.getPlayer().getPosition().getY(), 6);
        assertEquals(slider3.getPosition().getX(), 6);
        assertEquals(slider3.getPosition().getY(), 7);
        assertEquals(slider4.getPosition().getX(), 6);
        assertEquals(slider4.getPosition().getY(), 8);
        assertEquals(slider5.getPosition().getX(), 6);
        assertEquals(slider5.getPosition().getY(), 9);

        level.movePlayerTo(new Position(6,7));
        assertEquals(level.getPlayer().getPosition().getX(), 6);
        assertEquals(level.getPlayer().getPosition().getY(), 6);
        assertEquals(slider3.getPosition().getX(), 6);
        assertEquals(slider3.getPosition().getY(), 7);
        assertEquals(slider4.getPosition().getX(), 6);
        assertEquals(slider4.getPosition().getY(), 8);
        assertEquals(slider5.getPosition().getX(), 6);
        assertEquals(slider5.getPosition().getY(), 9);

        Slider slider6 = new VertSlider(6,5);
        level.addSlider(slider6);

        level.movePlayerTo(new Position(6,5));
        assertEquals(level.getPlayer().getPosition().getX(), 6);
        assertEquals(level.getPlayer().getPosition().getY(), 5);
        assertEquals(slider3.getPosition().getX(), 6);
        assertEquals(slider3.getPosition().getY(), 7);
        assertEquals(slider4.getPosition().getX(), 6);
        assertEquals(slider4.getPosition().getY(), 8);
        assertEquals(slider5.getPosition().getX(), 6);
        assertEquals(slider5.getPosition().getY(), 9);
        assertEquals(slider6.getPosition().getX(), 6);
        assertEquals(slider6.getPosition().getY(), 4);

    }

    @Test
    public void TestMovePlayerToWithInteractables() {
        Player player = new Player(4,5);
        FindSeqButton button1 = new FindSeqButton(5,5, 0, PuzzleButton.STATE.NOTCLEARED);
        Button button2 = new Button(7,6,1);
        level.addPlayer(player);
        level.addObjective(new Objective(9,8));
        level.addInteractable(button1);
        level.addInteractable(button2);

        level.movePlayerTo(new Position(5,5));
        assertEquals(player.getPosition(), new Position(4,5));

        button1.setState(PuzzleButton.STATE.CLEARED);
        level.movePlayerTo(new Position(5,5));
        assertEquals(player.getPosition(), new Position(5,5));

        level.movePlayerTo(new Position(7,6));
        assertEquals(player.getPosition(), new Position(7,6));
        level.movePlayerTo(new Position(6,6));
        assertEquals(player.getPosition(), new Position(6,6));

        button2.setState(Button.STATE.PRESSED);
        level.movePlayerTo(new Position(7,6));
        assertEquals(player.getPosition(), new Position(6,6));
    }

    @Test
    public void TestCheckInteractables() {
        Player player = new Player(9,9);
        Objective obj = new Objective(5,5);
        ColorSeqButton colorSeqButton = new ColorSeqButton(0,2,0, PuzzleButton.STATE.NOTCLEARED);
        FindSeqButton findSeqButton = new FindSeqButton(8,9,0, PuzzleButton.STATE.NOTCLEARED);
        level.addPlayer(player);
        level.addObjective(obj);
        level.addInteractable(colorSeqButton);
        level.addInteractable(findSeqButton);

        Interactable inter = level.checkInteractables();
        assertTrue(inter instanceof FindSeqButton);
        level.movePlayerTo(new Position(6,9));

        inter = level.checkInteractables();
        assertNull(inter);
    }

    @Test
    public void TestCheckObjective() {
        Player player = new Player(9,9);
        Objective obj = new Objective(5,5);
        level.addPlayer(player);
        level.addObjective(obj);

        level.checkObjective(player.getPosition(), obj.getPosition());
        assertFalse(level.isFinished());
        assertFalse(level.isNextLevel());

        player = new Player(obj.getPosition().getX(), obj.getPosition().getY());
        level.addPlayer(player);
        level.checkObjective(player.getPosition(), obj.getPosition());
        assertTrue(level.isFinished());
        assertTrue(level.isNextLevel());
    }

    @Test
    public void TestCheckKeys() {
        Player player = new Player(9, 9);
        Objective obj = new Objective(5, 5);
        Key key1 = new Key(9, 9, Key.Color.color1);
        Key key2 = new Key(5, 6, Key.Color.color2);
        level.addPlayer(player);
        level.addObjective(obj);
        level.addKey(key1);
        level.addKey(key2);

        assertEquals(0, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());
        assertEquals(2, level.getKeys().size());
        level.checkKeys(player.getPosition(), level.getKeys());
        assertEquals(1, level.getKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());

        level.checkKeys(player.getPosition(), level.getKeys());
        assertEquals(1, level.getKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());

        level.movePlayerTo(new Position(5, 6));
        level.checkKeys(player.getPosition(), level.getKeys());
        assertEquals(0, level.getKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color2).size());

    }

    @Test
    public void TestMovePlayerDoors() {
        Player player = new Player(0,2);
        Objective obj = new Objective(9,9);
        Door door1 = new Door(4,2, Key.Color.color1);
        Door door2 = new Door(5,2, Key.Color.color2);
        Door door3 = new Door(6,2, Key.Color.color1);
        Door door4 = new Door(7,2, Key.Color.color2);
        Key key1 = new Key(1,2, Key.Color.color2);
        Key key2 = new Key(2,2, Key.Color.color1);
        Key key3 = new Key(3,2, Key.Color.color1);
        level.addPlayer(player);
        level.addObjective(obj);
        level.addDoor(door1);
        level.addDoor(door2);
        level.addDoor(door3);
        level.addDoor(door4);
        level.addKey(key1);
        level.addKey(key2);
        level.addKey(key3);

        assertEquals(0, player.getPickedKeys().size());
        level.movePlayerTo(new Position(1,2));
        assertEquals(new Position(1,2), player.getPosition());
        assertEquals(1, player.getPickedKeys().size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color2).size());

        level.movePlayerTo(new Position(2,2));
        assertEquals(new Position(2,2), player.getPosition());
        assertEquals(2, player.getPickedKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color2).size());

        level.movePlayerTo(new Position(3,2));
        assertEquals(new Position(3,2), player.getPosition());
        assertEquals(3, player.getPickedKeys().size());
        assertEquals(2, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color2).size());


        assertEquals(4, level.getDoors().size());
        level.movePlayerTo(new Position(4,2));
        assertEquals(new Position(4,2), player.getPosition());
        assertEquals(2, player.getPickedKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color2).size());

        assertEquals(3, level.getDoors().size());
        level.movePlayerTo(new Position(5,2));
        assertEquals(new Position(5,2), player.getPosition());
        assertEquals(1, player.getPickedKeys().size());
        assertEquals(1, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());

        assertEquals(2, level.getDoors().size());
        level.movePlayerTo(new Position(6,2));
        assertEquals(new Position(6,2), player.getPosition());
        assertEquals(0, player.getPickedKeys().size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());

        assertEquals(1, level.getDoors().size());
        level.movePlayerTo(new Position(7,2));
        assertEquals(new Position(6,2), player.getPosition());
        assertEquals(0, player.getPickedKeys().size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color1).size());
        assertEquals(0, player.getPickedKeysByColor(Key.Color.color2).size());
        assertEquals(1, level.getDoors().size());


    }

    @Test
    public void TestInteract() {
        Command cmd = new InteractCommand(level, Mockito.mock(Audio.class));

        Player player = new Player(2,2);
        Objective obj = new Objective(5,5);
        ColorSeqButton colorSeqButton = Mockito.mock(ColorSeqButton.class);
        FindSeqButton findSeqButton = Mockito.mock(FindSeqButton.class);
        TilesPuzzleButton tilesPuzzleButton = Mockito.mock(TilesPuzzleButton.class);
        Button button = Mockito.mock(Button.class);
        Door buttonDoor = Mockito.mock(Door.class);

        Mockito.when(findSeqButton.getPosition()).thenReturn(new Position (5,5));
        Mockito.when(colorSeqButton.getPosition()).thenReturn(new Position (3,2));
        Mockito.when(tilesPuzzleButton.getPosition()).thenReturn(new Position (6,7));
        Mockito.when(button.getPosition()).thenReturn(new Position (1,0));
        Mockito.when(buttonDoor.getPosition()).thenReturn(new Position (9,9));

        level.addPlayer(player);
        level.addObjective(obj);
        level.addInteractable(colorSeqButton);
        level.addInteractable(findSeqButton);
        level.addInteractable(tilesPuzzleButton);
        level.addInteractable(button);
        level.setButtonDoor(buttonDoor);

        cmd.execute();
        Mockito.verify(findSeqButton,Mockito.times(0)).interact();
        Mockito.verify(colorSeqButton,Mockito.times(1)).interact();
        Mockito.verify(tilesPuzzleButton,Mockito.times(0)).interact();
        Mockito.verify(button,Mockito.times(0)).interact();

        level.movePlayerTo(new Position(4,5));
        cmd.execute();
        Mockito.verify(findSeqButton,Mockito.times(1)).interact();
        Mockito.verify(colorSeqButton,Mockito.times(1)).interact();
        Mockito.verify(tilesPuzzleButton,Mockito.times(0)).interact();
        Mockito.verify(button,Mockito.times(0)).interact();

        level.movePlayerTo(new Position(7,7));
        cmd.execute();
        Mockito.verify(findSeqButton,Mockito.times(1)).interact();
        Mockito.verify(colorSeqButton,Mockito.times(1)).interact();
        Mockito.verify(tilesPuzzleButton,Mockito.times(1)).interact();
        Mockito.verify(button,Mockito.times(0)).interact();

        int currentButton = level.getButtonNumber();
        assertNotNull(level.getButtonDoor());
        level.movePlayerTo(new Position(2,0));
        cmd.execute();
        Mockito.verify(findSeqButton,Mockito.times(1)).interact();
        Mockito.verify(colorSeqButton,Mockito.times(1)).interact();
        Mockito.verify(tilesPuzzleButton,Mockito.times(1)).interact();
        Mockito.verify(button,Mockito.times(1)).interact();
        assertEquals(currentButton + 1, level.getButtonNumber());
        assertNull(level.getButtonDoor());
    }

    @Test
    public void TestIsNear() { //TODO this doesnt have anything to do with Level
        Player player = new Player(0, 0);
        Wall wall = new Wall(1,0);

        assertTrue(player.isNear(wall));
        assertTrue(wall.isNear(player));
        wall.setPosition(new Position(2,0));
        assertFalse(player.isNear(wall));
        assertFalse(wall.isNear(player));
        player.setPosition(new Position(3,1));
        assertFalse(player.isNear(wall));
        assertFalse(wall.isNear(player));
        player.setPosition(new Position(2,1));
        assertTrue(player.isNear(wall));
        assertTrue(wall.isNear(player));
    }

}
