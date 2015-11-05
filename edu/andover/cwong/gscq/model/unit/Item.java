package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.items.*;

public class Item {

    private String ItemID;
    private ItemType thisItem;

    public Item(String itemName) {
        ItemID = itemName;
        assignItem();
    }

    public String getItemName() {
        return ItemID;
    }
    
    public ItemType getItem() {
    	return thisItem;
    }
    
    private void assignItem() {
    	if (ItemID.equals("Badge")) {
    		thisItem = new Badge();
    	}
    }
    
}
