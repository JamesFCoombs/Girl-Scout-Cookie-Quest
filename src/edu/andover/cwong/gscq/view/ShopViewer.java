package edu.andover.cwong.gscq.view;

import edu.andover.cwong.gscq.GSCQRunner;
import edu.andover.cwong.gscq.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShopViewer {
    
    //holds reference to the runner and the Game
    private GSCQRunner runner;
    private Game owner;
    
    @FXML
    private Label cookies;

    @FXML
    private Label money;

    @FXML
    private void handleExit(){
        owner.exitShop();
        runner.initRoot();
        runner.initContainer();
    }
    
    public void setRunner(GSCQRunner r) {
        this.runner = r;
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }

}
