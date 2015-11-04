package edu.andover.cwong.gscq.model.items;

public class Hairbrush extends ItemType{

	public String getDescription() {
		return "Gain 2 attack\nwhen equipped.";
	}

	public int attackIncrease() {
		return 2;
	}

	public int defenseIncrease() {
		return 0;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
