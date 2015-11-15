package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Room;

public class Enemy extends LivingGameEntity{
	
	
	private static final int BASE_ATTACK = 3;
	private Room room;
	
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
		
		super.update();
		
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
		if (room == null) {
			// TODO Continue pathing.
		} else if (room.isInRoom(player)) {
			// Moves towards the player.
			if (player.getXLoc() > xLocation) {
				move(2);
			} else if (player.getXLoc() < xLocation) {
				move(4);
			} else if(player.getYLoc() > yLocation) {
				move(3);
			} else {
				move(4);
			}
		} else {
			// TODO Move to a new room.
		}
	}
	
	// ------- GET AND SET METHODS -------
	
	public void setRoom(Room room) {
		this.room = room;
	}

}
