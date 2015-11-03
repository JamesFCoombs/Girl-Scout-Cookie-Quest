package edu.andover.cwong.gscq.model.unit;

public class Enemy extends LivingGameEntity{
	
	private Player player;
	private static final int BASE_ATTACK = 3;
	
	public Enemy(int xLoc, int yLoc) {
		super(xLoc, yLoc);
		initializeMaxHealth(10);
		setDefense(0);
		setAttack(0);
		setBaseAttack(BASE_ATTACK);
		setAttackRange(1);
	}
	
	public Enemy(int xLoc, int yLoc, int hp, int att, int def, int attRange) {
		super(xLoc, yLoc);
		initializeMaxHealth(hp);
		setDefense(def);
		setAttack(att);
		setBaseAttack(BASE_ATTACK);
		setAttackRange(attRange);
	}
	
	public void update() {
		
		if (canAttack(player)) {
			attackPlayer();
			return;
		}
		
		calculatePathing();
	}
	
	//  ------- PRIVATE METHODS -------
	
	private void attackPlayer() {
		player.takeDamage(calculateDamage(this, player));
	}
	
	private void calculatePathing() {
		// Stuff
	}
	
	// ------- GET AND SET METHODS -------
	
	public void setPlayer(Player plr) {
		player = plr;
	}

}
