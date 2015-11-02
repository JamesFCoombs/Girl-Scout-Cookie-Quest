package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Floor;

public abstract class GameEntity {
	
	int xLocation;
	int yLocation;
	
	private Floor curFloor;
	
	public void remove() {
		//Stuff
	}
	
	public boolean isInMap() {
		//Stuff
		return true;
	}
	
	public void setFloor(Floor newFloor) {
		curFloor = newFloor;
	}
	
	// ------- GET AND SET METHODS -------
	
	public void setXLoc(int xLoc) {
		xLocation = xLoc;
	}
	
	public void setYLoc(int yLoc) {
		yLocation = yLoc;
	}
	
	public int getXLoc() {
		return xLocation;
	}
	
	public int getYLoc() {
		return yLocation;
	}
}
