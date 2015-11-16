package edu.andover.cwong.gscq.control;

import javafx.scene.input.KeyEvent;

import edu.andover.cwong.gscq.model.Game;

// Feeds key input into the Game object.
public class KeyController {
    private Game owner;
    
    @SuppressWarnings("incomplete-switch")
    public void handleKeyInput(KeyEvent e) {
        // turns the input into usable int
        switch (e.getCode()) {
        case DOWN:
            owner.update(3);
            break;
        case UP:
            owner.update(1);
            break;
        case LEFT:
            owner.update(4);
            break;
        case RIGHT:
            owner.update(2);
            break;
        case I:     
        	owner.seeInventory(5);
        	break;
        }
    }
    
    public KeyController(Game owner) { this.owner = owner; }
}
