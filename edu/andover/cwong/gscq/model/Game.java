
package edu.andover.cwong.gscq.model;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.ItemEntity;
import edu.andover.cwong.gscq.model.unit.Player;
import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.nav.Tile;
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
    
    public Floor getFloor(){
    	return currFloor;
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
		Floor floor = new Floor(10, 10);
		
		Player player = new Player(8, 8);
		Enemy enemy1 = new Enemy(3, 3);
		Enemy enemy2 = new Enemy(8, 7);
		Tile newTile= new Tile(1);
		ItemEntity badge = new ItemEntity(8, 9, "Sash");
		
		enemy1.setPlayer(player);
		enemy2.setPlayer(player);
		
		floor.addGameEntity(player);
		floor.addGameEntity(enemy1);
		floor.addGameEntity(enemy2);
		floor.addGameEntity(badge);
		floor.setTile(2, 2, newTile);
		
		player.setFloor(floor);
		enemy1.setFloor(floor);
		enemy2.setFloor(floor);
		badge.setFloor(floor);
		return new Game(floor);
//        return new Game(FloorLoader.loadFloor("floor.txt"));
    }
    
    private Game(Floor f) {
        this.pc = Player.init();
        this.currFloor = f;
        this.inventory = new ArrayList<>();
    }
}
