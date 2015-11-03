package edu.andover.cwong.gscq.model.unit;

public class ItemEntity extends GameEntity {

    private String ItemName;

    public ItemEntity(int xLoc, int yLoc, String name) {
        xLocation = xLoc;
        yLocation = yLoc;
        ItemName = name;
    }

    public void dealWithCollision(GameEntity other) {
        if (other.addItem(ItemName)) {
            remove();
        }
    }

    public boolean addItem(String itemName) {
        return false;
    }

}
