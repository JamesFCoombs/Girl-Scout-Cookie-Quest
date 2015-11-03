package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Floor;

public abstract class GameEntity {
	
	int xLocation;
	int yLocation;
	
	private Floor curFloor;
	
	public void remove() {
		curFloor.removeGameEntity(this);
	}
	
	public boolean isInMap() {
		if (curFloor.getWidth() < xLocation || curFloor.getHeight() < yLocation) {
			return false;
		}
		return true;
	}
	
	// ------- ABSTRACT -------
	
	// GameEntity other is the thing that moved into this GameEntity.
	public abstract void dealWithCollision(GameEntity other);
	
	public abstract boolean addItem(String itemName);
	
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
