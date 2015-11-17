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
    
    // Updates the game state based on input from the player
    // This method should only be called when the player takes some action
    public void update(int input) {
        if (pc.move(input)) {
            currFloor.step();
            if (pc.getCurHealth() <= 0) { gameOver = true; }
        }
    }
    
    public Floor currentFloor() { return currFloor; }
    
    // This returns which tile a game entity (an item is on.
    // getters and setters
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
    	return currFloor.getUnits();
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
    	return String.format("%s", pc.getCookieCount());
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
        if (genFloor) {
            throw new UnsupportedOperationException(
                    "Floor generation is hard");
        }
        return new Game(FloorLoader.loadFloor("res/floor.txt"));
    }
    
    // creates entities for us to test
    // This sets up the floor.
    private Game(Floor f) {
        this.pc = Player.init();
        pc.setFloor(f);
        this.currFloor = f;
        Enemy enemy1=new Enemy(3,3);
        enemy1.setPlayer(pc);
        this.currFloor.addGameEntity(enemy1);
        enemy1.setFloor(this.currFloor);
        ItemEntity sash = new ItemEntity(8, 9, "Sash");
        this.currFloor.addGameEntity(sash);
        ItemEntity mascara = new ItemEntity(3, 9, "Mascara");
        this.currFloor.addGameEntity(mascara);
        ItemEntity plainCookie = new ItemEntity(5, 10, "PlainCookie");
        this.currFloor.addGameEntity(plainCookie);
        mascara.setFloor(this.currFloor);
        sash.setFloor(this.currFloor);
        plainCookie.setFloor(this.currFloor);
    }
}
