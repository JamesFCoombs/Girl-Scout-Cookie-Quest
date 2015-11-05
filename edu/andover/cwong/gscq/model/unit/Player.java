package edu.andover.cwong.gscq.model.unit;

public class Player extends LivingGameEntity {

    public Player(int xLoc, int yLoc) {
        super(xLoc, yLoc);
        initializeMaxHealth(10);
        setDefense(0);
        setAttack(0);
        setBaseAttack(6);
    }
    
    
    public void remove() {
    	System.out.println("End the game.");
    	super.remove();
    }
}
