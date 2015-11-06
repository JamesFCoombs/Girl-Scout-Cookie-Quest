package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import edu.andover.cwong.gscq.model.Game;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    private Game owner;
    @FXML
    private Label hpLabel;
    @FXML
    private Label atkLabel;
    @FXML
    private Label defLabel;

	@FXML
	private GridPane gameGrid;

	private ImageView[] visibleTiles;

    private final int tiles = 36;
    private final int tilesPerRow = 6;
	
	
    public void init() {
	    visibleTiles = new ImageView[tiles];
	    for (int i=0; i<tiles; i++) {
	        ImageView tileImage = new ImageView(
	                new Image(String.format("file:res/%d.png",
	                        owner.getTile(
	                                i%tilesPerRow-3+owner.getPlayerXLoc(),
	                                i/tilesPerRow-3+owner.getPlayerYLoc()
	                        ).getID()
	                ))
	        );
	        visibleTiles[i] = tileImage;
	        gameGrid.add(visibleTiles[i], i%tilesPerRow, i/tilesPerRow);
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
