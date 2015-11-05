package edu.andover.cwong.gscq.model.items;

public class Purse extends ItemType {
	public String getDescription() {
		return "Gain 1 attack\nwhen equipped.";
	}

	public int attackIncrease() {
		return 1;
	}

	public int defenseIncrease() {
		return 0;
	}
	
	public int healthIncrease () {
		return 0;
	}

}
