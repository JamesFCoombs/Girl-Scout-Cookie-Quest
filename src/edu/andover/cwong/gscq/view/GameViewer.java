package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import edu.andover.cwong.gscq.GSCQRunner;
import edu.andover.cwong.gscq.model.Game;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.Player;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    
    //holds reference to the runner and the Game
    private GSCQRunner runner;
    private Game owner;
    
    
    //all of the FXML things
    @FXML
    private Label hpLabel;
    @FXML
    private Label atkLabel;
    @FXML
    private Label defLabel;
    @FXML
    private Label cookieLabel;
    @FXML
    private Label ivtLabel;

	@FXML
	private GridPane gameGrid;
	
	@FXML
	private GridPane entityGrid;

    private ImageView[] visibleTiles;
    
    @FXML
    public void displayMinimap() { runner.displayMinimap(); }
    @FXML
    public void displayControls() { runner.displayControls(); }
    
    private final int tiles = 36;
    private final int tilesPerRow = 6;
    
    // FIXME: Change this to just make it better in general
    public void refreshCanvas(){
        entityGrid.getChildren().clear();
    	visibleTiles = new ImageView[tiles];
	    for (int i=0; i<tiles; i++) {
	        // get entities
	        GameEntity e = owner.getEntity(
                    i%tilesPerRow-(tilesPerRow/2)+owner.getPlayerXLoc(),
                    i/tilesPerRow-(tilesPerRow/2)+owner.getPlayerYLoc()
            );
	        // Display the entities on each tile
            if (e!=null){
                ImageView entityImage = new ImageView(
                        new Image("file:res/0.png")
                );
                if(e instanceof Player){
                    entityImage = new ImageView(
                            new Image("file:res/char.png")
                    );
                }
                else if(e instanceof Enemy){
                    entityImage = new ImageView(
                            new Image("file:res/enemy.png")
                    );
                }
                entityGrid.add(entityImage, 
                        3+e.getXLoc()-owner.getPlayerXLoc(), 
                        3+e.getYLoc()-owner.getPlayerYLoc()
                );
            }
            // Display the tile image (N.png for tile ID N)
	    	if (visibleTiles[i]==null){
		        ImageView tileImage = new ImageView(
		                new Image(String.format("file:res/%d.png",owner.getTile(
		                    i%tilesPerRow-(tilesPerRow/2)+owner.getPlayerXLoc(),
		                    i/tilesPerRow-(tilesPerRow/2)+owner.getPlayerYLoc()
		                ).getID()))
		        );
	        visibleTiles[i] = tileImage;
	    	}
	        gameGrid.add(visibleTiles[i], i%tilesPerRow, i/tilesPerRow);
	    }
    }
    
    public void refreshEntities(){
        // for each entity, refresh its position on the screen
    	for (GameEntity[] e:owner.getEntities()){
    		if (e!=null){
	    		for (GameEntity entity:e){
		        	if (entity!=null){
                        ImageView entityImage = new ImageView(
                                new Image("file:res/0.png")
                        );
                        if(entity instanceof Player){
                            entityImage = new ImageView(
                                    new Image("file:res/char.png")
                            );
                        }
                        else if(entity instanceof Enemy){
                            entityImage = new ImageView(
                                    new Image("file:res/enemy.png")
                            );
                        }
		    	        entityGrid.add(entityImage, 
		    	                3+entity.getXLoc()-owner.getPlayerXLoc(), 
		    	                3+entity.getYLoc()-owner.getPlayerYLoc()
		    	        );
	        		}
	    		}
    		}
    	}
    }
    
    // XXX - why is this here?
    public void toggleEquip(){
        for (Item a: owner.getInventory()){
            a.toggleEquip();
        }
    }

    public void refreshHUD() {
        hpLabel.setText(owner.formatPlayerHP());
        atkLabel.setText(owner.formatPlayerAtk());
        defLabel.setText(owner.formatPlayerDef());
        cookieLabel.setText(owner.formatCookieCount());
        // TODO
        String inventory="Inventory: \n";
        for (Item a : owner.getInventory()){
            if (a == null) { continue; }
            inventory+=a.getItemID()+"\n";
        }
        ivtLabel.setText(inventory);
    }
    
    public void setRunner(GSCQRunner r) {
        this.runner = r;
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }
}
