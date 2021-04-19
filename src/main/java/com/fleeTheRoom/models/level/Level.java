package com.fleeTheRoom.models.level;

import com.fleeTheRoom.models.Position;
import com.fleeTheRoom.models.puzzles.PuzzleButton;

import java.util.ArrayList;
import java.util.List;

public class Level {

    private final int width;
    private final int height;

    private final int difficulty;

    private final int id;

    private Player player;
    private Objective objective;

    private final List<Wall> walls;
    private final List<Key> keys;
    private final List<Door> doors;
    private final List<Slider> sliders;

    private final List<Interactable> interactables;

    private int buttonNumber;

    private Door buttonDoor;

    private final List<LevelObserver> observers;

    private boolean finished;   /*Exit game*/
    private boolean nextLevel;  /*Entered objective*/

    public Level(int width, int height, int difficulty, int id) {
        this.width = width;
        this.height = height;
        this.difficulty = difficulty;
        this.id = id;
        this.player = null;
        this.walls = new ArrayList<>();
        this.keys = new ArrayList<>();
        this.doors = new ArrayList<>();
        this.sliders = new ArrayList<>();
        this.interactables = new ArrayList<>();

        this.buttonNumber = 1;
        this.buttonDoor = null;

        this.finished = false;
        this.nextLevel = false;
        this.observers = new ArrayList<>();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void addPlayer(Player player) {
        this.player = player;
    }

    public Position getPlayerPos() {
        return this.player.getPosition();
    }

    private Entity getCollidingElement(Position position, List<? extends Entity> entities) {
        for (Entity entity : entities)
            if (entity.getPosition().equals(position))
                return entity;

        return null;
    }

    private boolean canMove(Position position) {
        if (position.getX() < 0 || position.getX() >= width) return false;
        if (position.getY() < 0 || position.getY() >= height) return false;

        Wall wall = (Wall) getCollidingElement(position, walls);
        Door door = (Door) getCollidingElement(position, doors);
        Slider slider = (Slider) getCollidingElement(position, sliders);
        Interactable interactable = (Interactable) getCollidingElement(position, interactables);

        if(buttonDoor != null && buttonDoor.getPosition().equals(position))
            return false;
        else if(door != null) {
            return openDoor(door);
        }
        else if(slider != null)
        {
            return moveSlider(player.getPosition(), slider);
        }
        else if(interactable != null) {
            if(interactable instanceof PuzzleButton)
                return ((PuzzleButton)interactable).getState().equals(PuzzleButton.STATE.CLEARED);
            else if(interactable instanceof Button)
                return ((Button)interactable).getState() == Button.STATE.NOTPRESSED;
            else return false;
        }
        else
            return wall == null;
    }

    public void movePlayerTo(Position position) {

        if (canMove(position)) {
            player.setPosition(position);
            checkKeys(player.getPosition(), keys);
            checkObjective(player.getPosition(), objective.getPosition());
        }

        notifyObservers();
    }
    private boolean canMoveSlider(Position oldPosition, Position position) {
        if (position.getX() < 0 || position.getX() >= width) return false;
        if (position.getY() < 0 || position.getY() >= height) return false;

        Entity collider = getCollidingElement(position, getAllEntities());
        if(collider instanceof Slider) {
            return moveSlider(oldPosition, (Slider) collider);
        }
        else return collider == null;
    }

    private boolean moveSlider(Position playerPos, Slider slider) {
        Position moveTo = slider.getMovedPosition(playerPos);
        if(!moveTo.equals(slider.getPosition()))
            if(canMoveSlider(slider.getPosition(),moveTo)) {
                slider.setPosition(moveTo);
                return true;
            }
        return false;
    }

    private boolean openDoor(Door door) {

        if(player.getPickedKeysByColor(door.getColor()).size() > 0) {
            doors.remove(door);
            player.useKey(door.getColor());
            return true;
        }
        return false;
    }

    public void checkKeys(Position playerPos, List<Key> keys) {
        for(Key key : keys) {
            if(playerPos.equals(key.getPosition())) {
                pickUpKey(key);
                player.addKey(key);
                break;
            }
        }
    }

    private void pickUpKey(Key key) {
        keys.remove(key);
    }

    public void checkObjective(Position playerPos, Position objPos) {
        if(playerPos.equals(objPos)) {
            nextLevel = true;
            finished = true;
        }
    }

    public Interactable checkInteractables() {
        for(Interactable interactable : interactables) {
            if(player.isNear(interactable))
                return interactable;
        }
        return null;
    }

    public void finish() {
        this.finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public void addWall(Wall wall) {
        walls.add(wall);
        this.notifyObservers();
    }

    public void addObserver(LevelObserver observer){
        observers.add(observer);
    }

    public void notifyObservers(){
        for(LevelObserver observer : observers){
            observer.levelChanged();
        }
    }

    public void addObjective(Objective objective) { this.objective = objective; }

    public void addKey(Key key) { this.keys.add(key); }

    public void addDoor(Door door) { this.doors.add(door); }

    public void addSlider(Slider slider) { this.sliders.add(slider); }

    public void addInteractable(Interactable interactable) { this.interactables.add(interactable); }

    public List<Entity> getAllEntities() {
        List<Entity> entities = new ArrayList<>();

        entities.add(player);
        entities.addAll(walls);
        entities.add(objective);
        entities.addAll(doors);
        entities.addAll(keys);
        entities.addAll(sliders);
        entities.addAll(interactables);

        return entities;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Key> getKeys() {
        return keys;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public Objective getObjective() {
        return objective;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Slider> getSliders() {
        return sliders;
    }

    public List<LevelObserver> getObservers() {
        return observers;
    }

    public int getButtonNumber() { return buttonNumber; }

    public boolean isNextLevel() {
        return nextLevel;
    }

    public void goNextLevel() {
        nextLevel = true;
    }

    public int getId() {
        return id;
    }

    public List<Interactable> getInteractables() {
        return this.interactables;
    }

    public void dontNextLevel() {
        this.nextLevel = false;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void advanceButton() {
        this.buttonNumber++;
    }

    public void setButtonDoor(Door buttonDoor) {
        this.buttonDoor = buttonDoor;
    }

    public Door getButtonDoor() {
        return buttonDoor;
    }

    public int getUnpressedButtons() {
        int result = 0;
        for(Interactable interactable : interactables) {
            if(interactable instanceof Button && ((Button) interactable).getState() == Button.STATE.NOTPRESSED)
                result++;
        }
        return result;
    }

    public void openButtonDoor() {
        this.buttonDoor = null;
    }
}
