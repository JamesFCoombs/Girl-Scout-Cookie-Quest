package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.items.*;

public class ItemEntity extends GameEntity {

	// The identifier for this item.
    private String itemName;
    
    // The item this entity represents.
    private Item thisItem;

    // Generates the item entity.
    public ItemEntity(int xLoc, int yLoc, String name) {
        xLocation = xLoc;
        yLocation = yLoc;
        itemName = name;
        
        // Determines what item this entity represents.
        setThisItem();
    }

    // If something moves onto this ItemEntity, adds the appropriate item
    // to the LivingGameEntity's inventory.
    public void dealWithCollision(LivingGameEntity other) {
        if (other.addItem(thisItem)) {
            remove();
        }
    }

    // Allows for two items to share space on the map.
    public boolean addItem(Item item) {
        return false;
    }
    
    // Does not need to revert movement, as movement is impossible!
    public void revertMovement() {}

    // Does nothing when updated, other than look pretty.
    public void update() {}
    
    // Sets this ItemEntity's item based off of its itemName.
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
    	}else if (itemName.equals("CootieShot")) {
    		thisItem = new CootieShot();
    	}else if (itemName.equals("PlainCookie")) {
    		thisItem = new PlainCookie();
    	}
    	
    }

}
