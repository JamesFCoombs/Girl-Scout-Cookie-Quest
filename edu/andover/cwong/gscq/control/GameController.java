package edu.andover.cwong.gscq.control;

import edu.andover.cwong.gscq.*;
import edu.andover.cwong.gscq.model.nav.Floor;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class GameController {
	private final int VISIBLEHEIGHT=5, VISIBLEWIDTH=5;
	
	private GSCQRunner mainApp;
	private Floor gameFloor;

	@FXML
	private ImageView A0, A1, A2, A3, A4, A5,
	B0,B1,B2,B3,B4,B5,
	C0,C1,C2,C3,C4,C5,
	D0,D1,D2,D3,D4,D5,
	E0,E1,E2,E3,E4,E5,
	F0,F1,F2,F3,F4,F5;
	
	ImageView[][] visibleGrid= new ImageView[][]{
			{A0,A1,A2,A3,A4,A5},
			{B0,B1,B2,B3,B4,B5},
			{C0,C1,C2,C3,C4,C5},
			{D0,D1,D2,D3,D4,D5},
			{E0,E1,E2,E3,E4,E5},
			{F0,F1,F2,F3,F4,F5}
			};
	
	@FXML
	private Label swag;

	@FXML
	private AnchorPane gamePanel;
	@FXML
	private AnchorPane hudPanel;
	
	
    public void showMove(){
    	for (int h=0;h<VISIBLEHEIGHT;h++){
    		for (int w=0;w<VISIBLEWIDTH;w++){
    			int ID=gameFloor.getTile(w, h).getID();
    			visibleGrid[h][w].setImage(new Image("file:" + ID + ".png"));
    		}
    	}
    }
    
	public void test(){
		swag.setText("jiofsdjofiew");
	}
	
    public void setOwner(GSCQRunner mainApp) {
        this.mainApp = mainApp;
    }
    public void setGameFloor(Floor floor){
    	this.gameFloor=floor;
    }
}
