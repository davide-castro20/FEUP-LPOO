package com.fleeTheRoom.controllers.commands.level;

import com.fleeTheRoom.controllers.commands.Command;
import com.fleeTheRoom.models.level.Button;
import com.fleeTheRoom.models.level.Interactable;
import com.fleeTheRoom.models.level.Level;
import com.fleeTheRoom.models.puzzles.PuzzleButton;
import com.fleeTheRoom.models.puzzles.colorSeq.ColorSeqButton;
import com.fleeTheRoom.models.puzzles.findSeq.FindSeqButton;
import com.fleeTheRoom.models.puzzles.tiles.TilesPuzzleButton;
import com.fleeTheRoom.view.Audio;

public class InteractCommand implements Command {
    private final Level level;
    private final Audio audio;

    public InteractCommand(Level level, Audio audio) {
        this.level = level;
        this.audio = audio;
    }

    @Override
    public void execute() {
        Interactable interactable = level.checkInteractables();
        if(interactable != null) {
            if (interactable instanceof PuzzleButton) {
                if (interactable instanceof FindSeqButton) {
                    audio.update(Audio.STATE.FINDSEQ);
                } else if (interactable instanceof ColorSeqButton) {
                    audio.update(Audio.STATE.COLORSEQ);
                } else if (interactable instanceof TilesPuzzleButton) {
                    audio.update(Audio.STATE.TILE);
                }
                interactable.interact();
                audio.update(Audio.STATE.LEVEL);
            }
            else if (interactable instanceof Button) {
                level.advanceButton();
                interactable.interact();
                if(level.getUnpressedButtons() == 0 && level.getButtonDoor() != null) {
                    level.openButtonDoor();
                }
            }
        }
        level.notifyObservers();
    }
}
