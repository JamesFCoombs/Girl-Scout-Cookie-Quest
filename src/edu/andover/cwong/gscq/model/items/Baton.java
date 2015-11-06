package edu.andover.cwong.gscq.model.items;

public class Baton extends Item {
	
	public Baton() {
		ItemID = "Baton";
	}

	public String getDescription() {
		return "Whack them across\nthe head!\n+9 attack.";
	}

	public int attackIncrease() {
		return 9;
	}

	public int defenseIncrease() {
		return 0;
	}
	
	public int healthIncrease () {
		return 0;
	}
}
