package edu.andover.cwong.gscq.model.items;

public class Hairbrush extends Item{
    
    public Hairbrush() {
        ItemID = "Hairbrush";
    }

    public String getDescription() {
        return "Gain 2 attack when equipped.";
    }

    public int attackIncrease() {
        return 2;
    }

    public int defenseIncrease() {
        return 0;
    }
    
    public int healthIncrease () {
        return 0;
    }
}
