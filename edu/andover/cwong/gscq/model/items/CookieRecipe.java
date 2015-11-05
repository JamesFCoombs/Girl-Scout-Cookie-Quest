package edu.andover.cwong.gscq.model.items;

public abstract class CookieRecipe extends Item {
	
	public abstract String getDescription();
	
	private int counter;
	private int numStepsToIncrement;
	private int numCookiesToAdd;
	
	public int incrementCount() {
		counter++;
		if (counter >= numStepsToIncrement) {
			counter = 0;
			return numCookiesToAdd;
		}
		return 0;
	}
	
	public int attackIncrease() {
		return 0;
	}

	public int defenseIncrease() {
		return 0;
	}
	
	public int healthIncrease() {
		return 0;
	}

	public void setCounter(int stCnt) {
		counter = stCnt;
	}
	
	public void setNumStepsToIncrement(int NSTI) {
		numStepsToIncrement = NSTI;
	}
	
	public void setNumCookiesToAdd(int NCTA) {
		numCookiesToAdd = NCTA;
	}
	
}
