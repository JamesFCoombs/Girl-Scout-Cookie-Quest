package edu.andover.cwong.gscq.view;

import java.util.ArrayList;
import java.util.Iterator;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.geometry.Rectangle2D;

import edu.andover.cwong.gscq.GSCQRunner;
import edu.andover.cwong.gscq.view.FloorViewBuilder;
import edu.andover.cwong.gscq.model.Game;
import edu.andover.cwong.gscq.model.unit.GameEntity;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    public static final int MAPVIEW_WIDTH = 400;
    public static final int MAPVIEW_HEIGHT = 400;
    
    // Holds reference to the runner and the Game
    // We need a reference to the runner because the runner handles all of the
    // nasty I/O operations involving loading FXML files.
    private GSCQRunner runner;
    private Game owner;
    
    private int displayedFloor = 1;
    
    // The list of sprites currently needed on the floor
    private ArrayList<EntitySprite> sprites = new ArrayList<>();
    
    // All of the FXML things
    @FXML
    private Label hpLabel;
    @FXML
    private Label atkLabel;
    @FXML
    private Label defLabel;
    @FXML
    private Label cookieLabel;
    @FXML
    private Label floorLabel;
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
        for (GameEntity[] row : owner.getEntities()) {
            for (GameEntity ge : row) {
                if (ge != null) {
                    EntitySprite es = new EntitySprite(ge);
                    spriteView.getChildren().add(es);
                    sprites.add(es);
                }
            }
        }
        mapView.setImage(floorView);
        refreshHUD();
        refreshMapview();
        refreshEntities();
    }
    
    public void refreshMapview() {
        int tlX = owner.getPlayerXLoc() * ViewConstants.TILE_DIMENSIONS;
        int tlY = (owner.getPlayerYLoc()-1) * ViewConstants.TILE_DIMENSIONS;
        refreshEntities();
        mapView.setViewport(new Rectangle2D(
                tlX, tlY, MAPVIEW_WIDTH, MAPVIEW_HEIGHT
        ));
    }
    
    public void refreshEntities() {
        Iterator<EntitySprite> it = this.sprites.iterator();
        while (it.hasNext()) {
            EntitySprite s = it.next();
            if (!s.isInMap()) {
                s.setVisible(false);
                it.remove();
                continue;
            }
            s.updatePosition();
        }
    }
    
    public void updateFrame() {
        for (EntitySprite s : this.sprites) { s.refresh(); }
        if (this.displayedFloor != owner.getCurrentLevel()) {
            this.displayedFloor = owner.getCurrentLevel();
            this.setupFloorView();
        }
    }
    
    public void refreshHUD() {
        hpLabel.setText(owner.formatPlayerHP());
        atkLabel.setText(owner.formatPlayerAtk());
        defLabel.setText(owner.formatPlayerDef());
        cookieLabel.setText(owner.formatCookieCount());
        // It's kind of abusing type coersion, but it looks cleaner than any
        // alternative I could think of.
        floorLabel.setText("" + this.displayedFloor);
    }
    
    public void setRunner(GSCQRunner r) {
        this.runner = r;
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }
}
