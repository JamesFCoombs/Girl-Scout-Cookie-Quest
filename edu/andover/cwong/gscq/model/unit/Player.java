package edu.andover.cwong.gscq.model.unit;

public class Player extends LivingGameEntity {

    public Player(int xLoc, int yLoc) {
        super(xLoc, yLoc);
        initializeMaxHealth(10);
        setDefense(0);
        setAttack(0);
        setBaseAttack(6);
    }
    
<<<<<<< HEAD
    
=======
>>>>>>> 2cf1759a9ea0a422420ba3e30569afdf87937fff
    public void remove() {
    	System.out.println("End the game.");
    	super.remove();
    }
}
