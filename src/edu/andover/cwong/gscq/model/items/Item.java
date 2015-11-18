package edu.andover.cwong.gscq.model.items;

public abstract class Item {
    
    protected String ItemID;
    private boolean isEquipped;
    
    public boolean checkIfIsItem(String str) {
        return (ItemID.equals(str));
    }
    public abstract String getDescription();
    
    public abstract int attackIncrease();
    public abstract int defenseIncrease();
    public abstract int healthIncrease();

    public void setItemID(String id) {
        ItemID = id;
    }
    
    public String getItemID() {
        return ItemID;
    }
    
    public void toggleEquip() {
        isEquipped = !isEquipped;
    }
    
    public boolean isEquipped() {
        return isEquipped;
    }
}
