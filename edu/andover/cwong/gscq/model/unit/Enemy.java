package edu.andover.cwong.gscq.model.unit;

public class Enemy extends LivingGameEntity{
	
	private Player player;
	
	public Enemy(int xLoc, int yLoc) {
		super(xLoc, yLoc);
		initializeMaxHealth(10);
		setDefense(0);
		setAttack(0);
		setAttackRange(1);
	}
	
	public Enemy(int xLoc, int yLoc, int hp, int att, int def, int attRange) {
		super(xLoc, yLoc);
		initializeMaxHealth(hp);
		setDefense(def);
		setAttack(att);
		setAttackRange(attRange);
	}
	
	public void act() {
		
		if (canAttack(player)) {
			attackPlayer();
			return;
		}
		
		calculatePathing();
	}
	
	//  ------- PRIVATE METHODS -------
	
	private void attackPlayer() {
		//Stuff
	}
	
	private void calculatePathing() {
		// Stuff
	}
	
	// ------- GET AND SET METHODS -------
	
	public void setPlayer(Player plr) {
		player = plr;
	}

}
