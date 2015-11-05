package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Floor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public abstract class GameEntity extends Circle {
    int xLocation;
    int yLocation;
    int radius;

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
    
	Color color = new Color(Math.random(), Math.random(), Math.random(), 0.5);
	Circle view = new Circle(xLocation, yLocation, radius, color);
	public GameEntity(int xLocation, int yLocation, double radius) {
		super(xLocation, yLocation, radius);
		setFill(color);
	}
	
	public Circle getView() {
		return view; 
	}

    // ------- ABSTRACT -------

    // GameEntity other is the thing that moved into this GameEntity.
    public abstract void dealWithCollision(GameEntity other);

    public abstract boolean addItem(String itemName);
    
    public abstract void update();
    
    public abstract void revertMovement();

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
