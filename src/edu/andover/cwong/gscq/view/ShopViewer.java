package edu.andover.cwong.gscq.view;

import edu.andover.cwong.gscq.GSCQRunner;
import edu.andover.cwong.gscq.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ShopViewer {
    
    //holds reference to the runner and the Game
    private GSCQRunner runner;
    private Game owner;
    private final int PRICE=50;
    
    @FXML
    private Label price;
    
    @FXML
    private Label cookies;

    @FXML
    private void handleExit(){
        owner.exitShop();
        runner.initRoot();
        runner.initContainer();
    }

    @FXML 
    private void buyAttack(){
        if (owner.canBuy(PRICE)){
            owner.addAttack(1);
            owner.spendCookies(PRICE);
            refresh();
        }
    }

    @FXML 
    private void buyDefense(){
        if (owner.canBuy(PRICE)){
            owner.addDefense(1);
            owner.spendCookies(PRICE);
            refresh();
        }
    }

    @FXML 
    private void buyHealth(){
        if (owner.canBuy(PRICE)){
            owner.addHealth(3);
            owner.spendCookies(PRICE);
            refresh();
        }
    }
    
    @FXML
    private void initialize(){
        price.setText(Integer.toString(PRICE));
    }
    
    public void setRunner(GSCQRunner r) {
        this.runner = r;
    }
    
    public void refresh(){
        System.out.println(owner);
        cookies.setText(owner.formatCookieCount());
    }
    
    public void setOwner(Game g) {
        this.owner = g;
    }

}
