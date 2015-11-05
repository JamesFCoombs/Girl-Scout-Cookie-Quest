package edu.andover.cwong.gscq.view;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Label;

import edu.andover.cwong.gscq.model.Game;

// What JFX calls a "controller" for the game panel. Handles refreshing of
// view elements (etc)
public class GameViewer {
    private Game owner;
    
    @FXML
    AnchorPane gamePanel;
    @FXML
    Label hpLabel;
    @FXML
    Label atkLabel;
    @FXML
    Label defLabel;
    
    public void refreshHUD() {
        hpLabel.setText(owner.formatPlayerHP());
        atkLabel.setText(owner.formatPlayerAtk());
        defLabel.setText(owner.formatPlayerDef());
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }
}
