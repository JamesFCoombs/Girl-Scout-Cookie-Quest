package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;

import edu.andover.cwong.gscq.GSCQRunner;
import edu.andover.cwong.gscq.view.FloorViewBuilder;
import edu.andover.cwong.gscq.model.Game;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.Player;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    public static final int MAPVIEW_WIDTH = 400;
    public static final int MAPVIEW_HEIGHT = 400;
    
    // holds reference to the runner and the Game
    private GSCQRunner runner;
    private Game owner;
    
    // all of the FXML things
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
	private ImageView mapView;
	@FXML
	private AnchorPane spriteView;
    
    @FXML
    public void displayMinimap() { runner.displayMinimap(); }
    @FXML
    public void displayControls() { runner.displayControls(); }
    
    public void setupFloorView() {
        Image floorView = FloorViewBuilder.constructImage(owner.currentFloor());
        mapView.setImage(floorView);
        refreshMapview();
        refreshEntities();
    }
    
    public void refreshMapview() {
        int tlX = owner.getPlayerXLoc() * FloorViewBuilder.TILE_DIMENSIONS;
        int tlY = owner.getPlayerYLoc() * FloorViewBuilder.TILE_DIMENSIONS;
        mapView.setViewport(new Rectangle2D(
                tlX, tlY, MAPVIEW_WIDTH, MAPVIEW_HEIGHT
        ));
    }
    
    public void refreshEntities() {
        
    }
    
    public void updateFrame() {
        
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
