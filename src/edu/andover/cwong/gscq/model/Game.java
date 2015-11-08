
package edu.andover.cwong.gscq.model;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.ItemEntity;
import edu.andover.cwong.gscq.model.unit.Player;
import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.nav.Tile;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.items.Sash;

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
        pc.gainCookie(input);
    }
    
    public Tile getTile(int x, int y){
        if (x < 0 || y < 0) { return Floor.WALL; }
    	return currFloor.getTile(x, y);
    }
    public GameEntity getEntity(int x, int y){
        if (x < 0 || y < 0) { return null; }
        return currFloor.getEntity(x, y);
    }
    public int getPlayerXLoc(){
    	return pc.getXLoc();
    }
    public int getPlayerYLoc(){
    	return pc.getYLoc();
    }
    
    public GameEntity[][] getEntities(){
    	return currFloor.units;
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
    
    public String formatCookieCount() {
    	return String.format("%s", pc.getCookieCount());
    }
    
    public ArrayList<Item> getInventory() { return inventory; }
    
    // Initialize the first floor
    public static Game init(boolean genFloor) {
        if (genFloor) {
            throw new UnsupportedOperationException(
                    "Floor generation is hard");
        }
        return new Game(FloorLoader.loadFloor("res/floor.txt"));
    }
    
    private Game(Floor f) {
        this.pc = Player.init();
        pc.setFloor(f);
        this.currFloor = f;
        this.inventory = new ArrayList<>();
        Enemy enemy1=new Enemy(3,3);
        enemy1.setPlayer(pc);
        this.currFloor.addGameEntity(enemy1);
        enemy1.setFloor(this.currFloor);
        ItemEntity badge = new ItemEntity(8, 9, "Sash");
        this.currFloor.addGameEntity(badge);
        badge.setFloor(this.currFloor);
    }
}
