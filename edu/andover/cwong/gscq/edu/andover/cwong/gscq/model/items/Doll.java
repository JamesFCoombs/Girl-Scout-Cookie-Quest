package edu.andover.cwong.gscq.model.items;

public class Doll extends ItemType{

	public String getDescription() {
		return "The aura of the doll\n protects you./n+10 defense";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 10;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
