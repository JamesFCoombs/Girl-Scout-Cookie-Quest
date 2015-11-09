package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;

public class LivingGameEntity extends GameEntity {

    private int curHealth;
    private int maxHealth;
    private int defense;
    private int attack;
    private int baseAttack;
    private int attackRange;
    private ArrayList<Item> inventory;
    
    private int lastXLocation;
    private int lastYLocation;

    /**
     * GameEntity that can move initializer
     * @param xLoc
     * @param yLoc
     */
    public LivingGameEntity(int xLoc, int yLoc) {
        xLocation = xLoc;
        yLocation = yLoc;
        inventory = new ArrayList<Item>();
    }

    // ------- METHODS -------
    
    // Moves the Entity one unit in the specified direction
    // 1 is up
    // 2 is right
    // 3 is down
    // 4 is left
    /**
     * Moves the Entity one unit in the specified direction
     * 1 is up
     * 2 is right
     * 3 is down
     * 4 is left
     * @param direction
     * @return
     */
    public boolean move(int direction) {
    	lastXLocation = getXLoc();
    	lastYLocation = getYLoc();
    	
    	// down
        if (direction == 1) {
            setYLoc(getYLoc() - 1);
        // right
        } else if (direction == 2) {
            setXLoc(getXLoc() + 1);
        // up
        } else if (direction == 3) {
            setYLoc(getYLoc() + 1);
        // left
        } else if (direction == 4) {
            setXLoc(getXLoc() - 1);
        } else {
            throw new IllegalArgumentException(
                    "Invalid direction for LGE movement");
        }

        if (!isInMap()) {
        	revertMovement();
        	return false;
        }
        curFloor.unitHasMoved(this, xLocation, yLocation);
        
        return true;
    }

    public void takeDamage(int dmg) {
        curHealth -= dmg;
        if (curHealth <= 0) {
            remove();
        }
    }

    public boolean canAttack(LivingGameEntity other) {

        int xDistance = xLocation - other.getXLoc();
        int yDistance = yLocation - other.getYLoc();
        double distance = Math.sqrt(xDistance * xDistance + yDistance * yDistance);

        if (1.0 * attackRange >= distance) {
            return true;
        }
        
        return false;
    }

    public boolean addItem(Item item) {
        inventory.add(item);
        return true;
    }
    
    public boolean addCookie(CookieRecipe cookieRecipe) {
    	return false;
    }

    public void dealWithCollision(GameEntity other) {
        other.revertMovement();
    }

    public void remove() {
        super.remove();
        for (int i = 0; i < inventory.size(); i++) {
            getCurFloor().addGameEntity(new ItemEntity(xLocation, yLocation, inventory.get(i).getItemID()));
            inventory.remove(i);
        }
    }
    
    public void revertMovement() {
    	setXLoc(lastXLocation);
    	setYLoc(lastYLocation);
    }

    public void update() {
    	int bonusAttack = 0;
    	int bonusDefense = 0;
    	for (int i = 0; i < inventory.size(); i++) {
    		if (inventory.get(i).isEquipped()) {
    			bonusAttack += inventory.get(i).attackIncrease();
    			bonusDefense += inventory.get(i).defenseIncrease();
    		}
    	}
    	setAttack(bonusAttack);
    	setDefense(bonusDefense);
    }
    
    // ------- STATIC METHODS -------

    public static int calculateDamage(LivingGameEntity attacker, LivingGameEntity defender) {
        return attacker.getBaseAttack() + attacker.getAttack() - defender.getDefense();
    }

    // ------- SET AND GET METHODS -------

    // Sets maxHealth, and fills curHealth to max.
    public void initializeMaxHealth(int hp) {
        maxHealth = hp;
        setCurHealth(hp);
    }

    public void setMaxHealth(int hp) {
        maxHealth = hp;
    }

    public void setCurHealth(int hp) {
        curHealth = hp;
    }

    public void setDefense(int df) {
        defense = df;
    }

    public void setAttack(int att) {
        attack = att;
    }
    
    public void setBaseAttack(int att) {
    	baseAttack = att;
    }

    public void setAttackRange(int attRange) {
        attackRange = attRange;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getCurHealth() {
        return curHealth;
    }

    public int getDefense() {
        return defense;
    }

    public int getAttack() {
        return attack;
    }
    
    public int getBaseAttack() {
    	return baseAttack;
    }

    public int getAttackRange() {
        return attackRange;
    }
    
    public ArrayList<Item> getInventory() {
    	return inventory;
    }
    
    public int getLastXLocation() {
    	return lastXLocation;
    }
    
    public int getLastYLocation() {
    	return lastYLocation;
    }

}
