package edu.andover.cwong.gscq.model.unit;

public class LivingGameEntity extends GameEntity{
	
	private int curHealth;
	private int maxHealth;
	private int defense;
	private int attack;
	
	public LivingGameEntity(int xLoc, int yLoc) {
		xLocation = xLoc;
		yLocation = yLoc;
	}
	
	// ------- METHODS -------
	
	// Moves the Entity one unit in the specified direction
	// 1 is up
	// 2 is right
	// 3 is down
	// 4 is left
	public void move(int direction)
	{
		if (direction == 1) {
			setXLoc(getYLoc() - 1);
		} else if (direction == 2) {
			setXLoc(getXLoc() + 1);
		} else if (direction == 3) {
			setXLoc(getYLoc() + 1);
		} else if (direction == 4) {
			setXLoc(getXLoc() - 1);
		} else {
			throw new 
			IllegalArgumentException("Invalid direction for LGE movement");
		}
		
	}
	
	public void takeDamage(int dmg) {
		curHealth -= dmg;
		if (curHealth <= 0) {
			remove();
		}
	}
	
	
	
	// ------- SET AND GET METHODS -------
	
	// Sets maxHealth, and fills curHealth to max.
	public void initializeMaxHealth(int hp) {
		maxHealth = hp;
		setCurHealth(hp);
	}
	
	public void setMaxHealth(int hp) {
		maxHealth = hp;
	}

	public void setCurHealth(int hp) {
		curHealth = hp;
	}
	
	public void setDefense(int df) {
		defense = df;
	}
	
	public void setAttack(int att) {
		attack = att;
	}
	
	public int getMaxHealth() {
		return maxHealth;
	}
	
	public int getCurHealth() {
		return curHealth;
	}
	
	public int getDefense() {
		return defense;
	}
	
	public int getAttack() {
		return attack;
	}

}
