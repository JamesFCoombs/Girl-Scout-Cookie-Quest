package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import edu.andover.cwong.gscq.model.Game;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    private Game owner;
    
    @FXML
    Canvas gameCanvas;
    @FXML
    Label hpLabel;
    @FXML
    Label atkLabel;
    @FXML
    Label defLabel;


	@FXML
	private GridPane gameGrid ;


	private ImageView[] visibleTiles ;

    private final int numTiles = 36 ;
    private final int numTilesPerRow = 6 ;
	
	
    public void init(){
	    visibleTiles = new ImageView[numTiles];
	    for (int i=0; i<numTiles; i++) {
	        ImageView tileImage = new ImageView(new Image("file:res/"+
	    owner.getTile(i%numTilesPerRow,i/numTilesPerRow).getID()
	    +".png"));
	        visibleTiles[i] = tileImage ;
	        gameGrid.add(visibleTiles[i], i % numTilesPerRow, i / numTilesPerRow);
	    }
    }
    
    public void refreshCanvas() {
	    visibleTiles = new ImageView[numTiles];
	    for (int i=0; i<numTiles; i++) {
	        ImageView tileImage = new ImageView(new Image("file:res/"+owner.getTile(i%numTilesPerRow,i/numTilesPerRow).getID()+".png"));
	        visibleTiles[i] = tileImage ;
	        gameGrid.add(visibleTiles[i], i % numTilesPerRow, i / numTilesPerRow);
	    }
    }
    
    public void refreshHUD() {
        hpLabel.setText(owner.formatPlayerHP());
        atkLabel.setText(owner.formatPlayerAtk());
        defLabel.setText(owner.formatPlayerDef());
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }
}
