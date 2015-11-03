package edu.andover.cwong.gscq.model.items;

public class CookiePan extends ItemType{
	
	public String getDescription() {
		return "WHAM!!\n+6 attack.";
	}

	public int attackIncrease() {
		return 6;
	}

	public int defenseIncrease() {
		return 0;
	}

}
