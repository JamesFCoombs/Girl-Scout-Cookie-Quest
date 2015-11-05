package edu.andover.cwong.gscq.control;

import edu.andover.cwong.gscq.*;
import edu.andover.cwong.gscq.model.nav.Floor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class GameController {
	
	private GSCQRunner mainApp;
	private Floor gameFloor;
	

	@FXML
	private GridPane gameGrid ;

	@FXML
	private AnchorPane gamePanel;
	@FXML
	private AnchorPane hudPanel;

	private ImageView[] visibleTiles ;
	

	public void initialize() {
	    final int numTiles = 36 ;
	    final int numTilesPerRow = 6 ;

	    visibleTiles = new ImageView[numTiles];
	    for (int i=0; i<numTiles; i++) {
	        ImageView tileImage = new ImageView(new Image("file:res/3.png"));
	        visibleTiles[i] = tileImage ;
	        gameGrid.add(visibleTiles[i], i % numTilesPerRow, i / numTilesPerRow);
	    }
	}
	
	
	
    
    public void setOwner(GSCQRunner mainApp) {
        this.mainApp = mainApp;
    }
    public void setGameFloor(Floor floor){
    	this.gameFloor=floor;
    }
}
