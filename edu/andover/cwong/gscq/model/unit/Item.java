package edu.andover.cwong.gscq.model.unit;

public class Item {

    private String ItemID;

    public Item(String itemName) {
        ItemID = itemName;
    }

    public String getItemName() {
        return ItemID;
    }

}
