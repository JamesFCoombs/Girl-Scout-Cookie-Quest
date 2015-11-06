package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import edu.andover.cwong.gscq.model.Game;
import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.Player;

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
	
	@FXML
	private GridPane entityGrid;

	private ImageView[] visibleTiles;

    private final int tiles = 36;
    private final int tilesPerRow = 6;
	
	
    public void refreshCanvas() {
	    refreshTiles();
	    refreshEntities();
    }
    
    public void refreshTiles(){
    	visibleTiles = new ImageView[tiles];
	    for (int i=0; i<tiles; i++) {
	    	if (visibleTiles[i]==null){
		        ImageView tileImage = new ImageView(
		                new Image(String.format("file:res/%d.png",
		                        owner.getTile(
		                                i%tilesPerRow-(tilesPerRow/2)+owner.getPlayerXLoc(),
		                                i/tilesPerRow-(tilesPerRow/2)+owner.getPlayerYLoc()
		                        ).getID()
		                ))
		        );
	        visibleTiles[i] = tileImage;
	    	}
	        gameGrid.add(visibleTiles[i], i%tilesPerRow, i/tilesPerRow);
	    }
    }
    public void refreshEntities(){
    	for (GameEntity[] e:owner.getEntities()){
    		if (e!=null){
	    		for (GameEntity entity:e){
		        	if (entity!=null){
                        ImageView entityImage = new ImageView(
                                new Image("file:res/0.png"
                                )
                        );
                        if(entity instanceof Player){
                            entityImage = new ImageView(
                                    new Image("file:res/char.png"
                                    )
                            );
                        }
                        else if(entity instanceof Enemy){
                            entityImage = new ImageView(
                                    new Image("file:res/enemy.png"
                                    )
                            );
                        }
		    	        entityGrid.add(entityImage, 
		    	                3+entity.getXLoc()-owner.getPlayerXLoc(), 
		    	                3+entity.getYLoc()-owner.getPlayerYLoc());
	        		}
	    		}
    		}
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
