package edu.andover.cwong.gscq.model.items;

public class Doll extends ItemType{

	public String getDescription() {
		return "The aura of the doll\n protects you.";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 4;
	}
}
