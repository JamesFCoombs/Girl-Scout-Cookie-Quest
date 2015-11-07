package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import edu.andover.cwong.gscq.model.Game;
import edu.andover.cwong.gscq.model.items.Item;
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
    private Label ivtLabel;

	@FXML
	private GridPane gameGrid;
	
	@FXML
	private GridPane entityGrid;

    private ImageView[] visibleTiles;

    private final int tiles = 36;
    private final int tilesPerRow = 6;
    
    public void refreshCanvas(){
        entityGrid.getChildren().clear();
    	visibleTiles = new ImageView[tiles];
	    for (int i=0; i<tiles; i++) {
	        GameEntity e = owner.getEntity(
                    i%tilesPerRow-(tilesPerRow/2)+owner.getPlayerXLoc(),
                    i/tilesPerRow-(tilesPerRow/2)+owner.getPlayerYLoc());
            if (e!=null){
                ImageView entityImage = new ImageView(
                        new Image("file:res/0.png"
                        )
                );
                if(e instanceof Player){
                    entityImage = new ImageView(
                            new Image("file:res/char.png"
                            )
                    );
                }
                else if(e instanceof Enemy){
                    entityImage = new ImageView(
                            new Image("file:res/enemy.png"
                            )
                    );
                }
                entityGrid.add(entityImage, 
                        3+e.getXLoc()-owner.getPlayerXLoc(), 
                        3+e.getYLoc()-owner.getPlayerYLoc());
            }
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

    private String inventory="Inventory: \n";
    public void refreshHUD() {
        hpLabel.setText(owner.formatPlayerHP());
        atkLabel.setText(owner.formatPlayerAtk());
        defLabel.setText(owner.formatPlayerDef());
        for (Item a: owner.getInventory()){
            inventory+=a.getItemID()+"\n";
        }
        ivtLabel.setText(inventory);
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }
}
