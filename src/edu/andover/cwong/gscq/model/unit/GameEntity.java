package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.nav.Floor;
import edu.andover.cwong.gscq.model.nav.Tile;

public abstract class GameEntity {
    
    // The x and y location of this GameEntity.
    int xLocation;
    int yLocation;
    
    // A reference to the Player for all other classes to refer to.
    public static Player player;

    // The floor this GameEntity is currently on.
    protected Floor curFloor;
    protected Tile[][] floorTiles;

    // Tells the currentFloor to remove this GameEntity, making it incapable
    // of acting in the game.
    public void remove() {
        curFloor.removeGameEntity(this);
    }

    // Verifies the GameEntity is within the bounds of the current floor.
    public boolean isInMap() {
        return !(curFloor.getWidth() <= xLocation ||
            curFloor.getHeight() <= yLocation ||
            0 > xLocation || 0 > yLocation);
    }
    
    // ------- ABSTRACT -------

    // LivingGameEntity other has moved into this GameEntity.
    // ((Only LivingGameEntities can move so this is fine))
    public abstract void dealWithCollision(LivingGameEntity other);

    // Adds an item to a LivingGameEntity's inventory, or allows space to be
    // shared with another item.
    public abstract boolean addItem(Item item);
    
    // Calls on the GameEntity to move.
    public abstract void update();
    
    // Moves the GameEntity back to where it was previously.
    public abstract void revertMovement();
    

    // ------- CONVENIENCE METHODS -------
    
    public int distToPlayerX() {
        return this.xLocation - GameEntity.player.xLocation;
    }
    
    public int distToPlayerY() {
        return this.yLocation - GameEntity.player.yLocation;
    }
    
    // ------- GET AND SET METHODS -------
    public void setXLoc(int xLoc) {
        xLocation = xLoc;
    }

    public void setYLoc(int yLoc) {
        yLocation = yLoc;
    }

    public void setFloor(Floor newFloor) {
        curFloor = newFloor;
        floorTiles = curFloor.getGrid();
    }

    public int getXLoc() {
        return xLocation;
    }

    public int getYLoc() {
        return yLocation;
    }

    public Floor getCurFloor() {
        return curFloor;
    }
}
