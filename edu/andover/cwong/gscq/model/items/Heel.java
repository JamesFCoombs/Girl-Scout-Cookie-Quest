package edu.andover.cwong.gscq.model.items;

public class Heel extends ItemType{
	
	public String getDescription() {
		return "Gains 8 extra attack\nwhen equipped.";
	}

	public int attackIncrease() {
		return 8;
	}

	public int defenseIncrease() {
		return 0;
	}
}
