package edu.andover.cwong.gscq.model.items;

public class Mascara extends Item {
	
	public Mascara() {
		ItemID = "Mascara";
	}
	
	public String getDescription() {
		return "Your attactiveness\n increase makes Boy Scouts\n swoon +4 defense.";
	}

	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 4;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
