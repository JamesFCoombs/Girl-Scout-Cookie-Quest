package edu.andover.cwong.gscq.model.items;

public class CootieShot extends ItemType {
	
	public String getDescription() {
		return "Protects against cooties!\n+2 health.";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 0;
	}
	
	public int healthIncrease () {
		return 2;
	}
}
