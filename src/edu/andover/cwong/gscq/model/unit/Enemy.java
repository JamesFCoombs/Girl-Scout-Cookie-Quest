package edu.andover.cwong.gscq.model.unit;

public class Enemy extends LivingGameEntity{
	
	// All enemy's have a base attack of 3.
	private static final int BASE_ATTACK = 3;

	public Enemy(int xLoc, int yLoc) {
		super(xLoc, yLoc);
		
		// Sets default stats for the enemy.
		initializeMaxHealth(5);
		setDefense(0);
		incBaseAttack(BASE_ATTACK);
		setAttackRange(1);
	}
	
	// Allows for a much more specialized enemy, with specific stats.
	public Enemy(int xLoc, int yLoc, int hp, int att, int def, int attRange) {
		super(xLoc, yLoc);
		initializeMaxHealth(hp);
		setDefense(def);
		setAttack(att);
		incBaseAttack(BASE_ATTACK);
		setAttackRange(attRange);
	}
	
	// When updated, the enemy searches for the player, and attacks the player
	// if she is within attack range.
	public void update() {
		
		super.update();
		
		if (canAttack(player)) {
			attackPlayer();
			return;
		}
		
		calculatePathing();
	}
	
	//  ------- PRIVATE METHODS -------
	
	// Attacks the player.
	private void attackPlayer() {
		player.takeDamage(calculateDamage(this, player));
	}
	
	// Searches for the player.
	private void calculatePathing() {
		// If the enemy is in a hallway, continues moving down the hall.
		if (room == null) {
			// TODO Continue pathing.
		// If the enemy and the player are in the same room, moves towards the
		// player.
		} else if (room.isInRoom(player)) {
			// Moves towards the player.
			if (player.getXLoc() > xLocation) {
				move(2);
			} else if (player.getXLoc() < xLocation) {
				move(4);
			} else if(player.getYLoc() > yLocation) {
				move(3);
			} else {
				move(1);
			}
		// If in a room other than the player's, moves out of it.
		} else {
			// TODO Move to a new room.
		}
	}
	
	// ------- GET AND SET METHODS -------
	


}
