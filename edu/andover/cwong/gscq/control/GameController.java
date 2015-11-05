package edu.andover.cwong.gscq.control;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import edu.andover.cwong.gscq.GSCQRunner;

public class GameController {
    private GSCQRunner owner;
    
    @FXML
    AnchorPane gamePanel;
    @FXML
    AnchorPane hudPanel;
    
    public void setOwner(GSCQRunner g) {
        this.owner = g;
    }
}
