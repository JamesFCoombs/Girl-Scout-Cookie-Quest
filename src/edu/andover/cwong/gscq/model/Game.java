
package edu.andover.cwong.gscq.model;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.ItemEntity;
import edu.andover.cwong.gscq.model.unit.Player;
import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.nav.Tile;
import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;

// A "container" class containing convenience "hooks" for a relevant view
// as well as encapsulating the entire game state at once.
public class Game {
    // Holds all of the relevant data for navigation
    private Floor currFloor;
    private Player pc;
    public boolean gameOver = false;
    public boolean showShop = true;
    
    // Updates the game state based on input from the player
    // This method should only be called when the player takes some action
    public void update(int input) {
        if (pc.move(input)) {
            currFloor.step();
            if (currFloor.getTile(
                    GameEntity.player.getXLoc(),GameEntity.player.getYLoc()).
                    getID()==3){
                showShop = true;
            }
            //if (pc.getCurHealth() <= 0) { gameOver = true; }
        }
    }
    
    public void seeInventory(int input, int item) {
    	if (pc.openInventory(input, item)) {
    		pc.selectItem(item);
    	}
    }
    
    // This returns which tile a game entity (an item is on.
    //getters and setters
    public Tile getTile(int x, int y){
    	// This detects if the game entity is within walls.
        if (x < 0 || y < 0) { return Floor.WALL; }
    	return currFloor.getTile(x, y);
    }
    
    // This returns which entity is on a specific tile.
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
    
    // This gets all the game entities on the floor.
    public GameEntity[][] getEntities(){
    	return currFloor.units;
    }
    
    // This displays both the current health of the player
    // and the maximum health that the player can have.
    public String formatPlayerHP() {
        int c_hp = pc.getCurHealth();
        int m_hp = pc.getMaxHealth();
        return String.format("%d/%d", c_hp, m_hp);
    }
    
    // This displays the player attack on the screen.
    public String formatPlayerAtk() {
        return String.format("%s", pc.getAttack());
    }
    
    // This displays the player defense on the screen.
    public String formatPlayerDef() {
        return String.format("%s", pc.getDefense());
    }
    
    // This displays the number of cookies that the player has
    // on the screen.
    public String formatCookieCount() {
    	return String.format("Cookies: %s", pc.getCookieCount());
    }
    
    // This gets the player's inventory.
    public ArrayList<Item> getInventory() { 
    	return pc.getInventory(); 
    }

    // This gets the player's list of cookies.
    public ArrayList<CookieRecipe> getCookieList() { 
    	return pc.getCookieList(); 
   	} 

    // Initialize the first floor
    public static Game init(boolean genFloor) {
    	new Player(0,0);
        if (genFloor) {
           return new Game(new Floor(40, 40));
        } 
        else {
        	return new Game(new Floor (40, 40));
        }
        
    }
    
    // creates entities for us to test
    // This sets up the floor.
    private Game(Floor f) {
    	this.pc = GameEntity.player;
        pc.setFloor(f);
        this.currFloor = f;
    }

    public void exitShop() {
        pc.move(3);
        showShop=false;
        System.out.println("set to false");
    }
}
