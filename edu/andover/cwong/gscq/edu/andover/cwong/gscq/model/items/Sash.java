package edu.andover.cwong.gscq.model.items;

public class Sash extends ItemType{
	public String getDescription() {
		return "Gain 1 defense\nwhen equipped.";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 1;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
