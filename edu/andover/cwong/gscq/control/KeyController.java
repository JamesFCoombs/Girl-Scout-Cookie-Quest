package edu.andover.cwong.gscq.control;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

import edu.andover.cwong.gscq.model.Game;

// Feeds key input into the Game object.
public class KeyController {
    private Game owner;
    
    public void handleKeyInput(KeyEvent e) {
        // Cam: these numbers don't make sense to me either
        switch (e.getCode()) {
        case UP:
            owner.update(3);
            break;
        case DOWN:
            owner.update(1);
            break;
        case LEFT:
            owner.update(4);
            break;
        case RIGHT:
            owner.update(2);
            break;
        }
    }
    
    public KeyController(Game owner) { this.owner = owner; }
}