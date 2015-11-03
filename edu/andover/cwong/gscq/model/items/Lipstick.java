package edu.andover.cwong.gscq.model.items;

public class Lipstick extends ItemType {
	public String getDescription() {
		return "Boys go crazy\n at your red lips.\n+6 defense";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 6;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
