package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.items.*;

public class ItemEntity extends GameEntity {

    private String itemName;
    private Item thisItem;

    public ItemEntity(int xLoc, int yLoc, String name) {
        xLocation = xLoc;
        yLocation = yLoc;
        itemName = name;
        setThisItem();
    }

    public void dealWithCollision(GameEntity other) {
        if (other.addItem(thisItem)) {
            remove();
        }
    }

    public boolean addItem(Item item) {
        return false;
    }
    
    public void revertMovement() {}

    public void update() {}
    
    private void setThisItem() {
    	if (itemName.equals("Badge")) {
    		thisItem = new Badge();
    	} else if (itemName.equals("Baton")) {
    		thisItem = new Baton();
    	}else if (itemName.equals("CookiePan")) {
    		thisItem = new CookiePan();
    	}else if (itemName.equals("Doll")) {
    		thisItem = new Doll();
    	}else if (itemName.equals("Hairbrush")) {
    		thisItem = new Hairbrush();
    	}else if (itemName.equals("Heel")) {
    		thisItem = new Heel();
    	}else if (itemName.equals("Lipstick")) {
    		thisItem = new Lipstick();
    	}else if (itemName.equals("Mascara")) {
    		thisItem = new Mascara();
    	}else if (itemName.equals("Purse")) {
    		thisItem = new Purse();
    	}else if (itemName.equals("Sash")) {
    		thisItem = new Sash();
    	}
    	
    	else if (itemName.equals("CootieShot")) {
    		thisItem = new CootieShot();
    	}
    }
}
