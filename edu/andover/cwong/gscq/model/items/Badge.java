package edu.andover.cwong.gscq.model.items;

public class Badge extends ItemType {
	public String getDescription() {
		return "Badge of honor.\n+2 defense.";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 2;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
