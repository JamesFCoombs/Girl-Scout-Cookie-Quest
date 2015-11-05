
package edu.andover.cwong.gscq.model;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.Player;
import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.items.Item;

// A "container" class containing convenience "hooks" for a relevant view
// as well as encapsulating the entire game state at once.
public class Game {
    // Holds all of the relevant data for navigation
    private Floor currFloor;
    private ArrayList<Item> inventory;
    private Player pc;
    
    // Updates the game state based on input from the player
    // This method should only be called when the player takes some action
    public void update(int input) {
        // BUG: currently updates the entire floor even if movement is
        // impossible. TODO
        pc.move(input);
        currFloor.step();
    }
    
    public String formatPlayerHP() {
        int c_hp = pc.getCurHealth();
        int m_hp = pc.getMaxHealth();
        return String.format("%d/%d", c_hp, m_hp);
    }
    
    public String formatPlayerAtk() {
        return String.format("%s", pc.getAttack());
    }
    
    public String formatPlayerDef() {
        return String.format("%s", pc.getDefense());
    }
    
    public ArrayList<Item> getInventory() { return inventory; }
    
    // Initialize the first floor
    public static Game init(boolean genFloor) {
        if (genFloor) {
            throw new UnsupportedOperationException(
                    "Floor generation is hard");
        }
        return new Game(FloorLoader.loadFloor("floor.txt"));
    }
    
    private Game(Floor f) {
        this.pc = Player.init();
        this.currFloor = f;
        this.inventory = new ArrayList<>();
    }
}
