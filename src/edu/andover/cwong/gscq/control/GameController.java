package edu.andover.cwong.gscq.control;

import edu.andover.cwong.gscq.*;
import edu.andover.cwong.gscq.model.nav.Floor;

public class GameController {
	
	private GSCQRunner mainApp;
	private Floor gameFloor;
	
	

	public void initialize() {
	}
	
	
    
    public void setOwner(GSCQRunner mainApp) {
        this.mainApp = mainApp;
    }
    public void setGameFloor(Floor floor){
    	this.gameFloor=floor;
    }
}
