package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.nav.Floor;

public abstract class GameEntity {
    int xLocation;
    int yLocation;
    int radius;
    public static Player player;

    protected Floor curFloor;

    public void remove() {
        curFloor.removeGameEntity(this);
    }

    public boolean isInMap() {
        if (curFloor.getWidth() <= xLocation ||
            curFloor.getHeight() <= yLocation ||
            0 > xLocation || 0 > yLocation
        ) {
            return false;
        }
        return true;
    }
    // ------- ABSTRACT -------

    // LivingGameEntity other is the thing that moved into this GameEntity.
    // ((Only LivingGameEntities can move so this is fine))
    public abstract void dealWithCollision(LivingGameEntity other);

    public abstract boolean addItem(Item item);
    
    public abstract void update();
    
    public abstract void revertMovement();
    
    public abstract boolean addCookie(CookieRecipe cookieRecipe);

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
