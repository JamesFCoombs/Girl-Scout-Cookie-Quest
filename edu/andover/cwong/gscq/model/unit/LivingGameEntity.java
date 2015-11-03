package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

public class LivingGameEntity extends GameEntity {

    private int curHealth;
    private int maxHealth;
    private int defense;
    private int attack;
    private int attackRange;
    private ArrayList<Item> inventory;

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
    public void move(int direction) {
        if (direction == 1) {
            setXLoc(getYLoc() - 1);
        } else if (direction == 2) {
            setXLoc(getXLoc() + 1);
        } else if (direction == 3) {
            setXLoc(getYLoc() + 1);
        } else if (direction == 4) {
            setXLoc(getXLoc() - 1);
        } else {
            throw new IllegalArgumentException("Invalid direction for LGE movement");
        }

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

        if (1.0 * attackRange > distance) {
            return true;
        }

        return false;
    }

    public boolean addItem(String itemName) {
        inventory.add(new Item(itemName));
        return true;
    }

    public void dealWithCollision(GameEntity other) {
        // Stuff
    }

    public void remove() {
        for (int i = 0; i < inventory.size(); i++) {
            getCurFloor().addGameEntity(new ItemEntity(xLocation, yLocation, inventory.get(i).getItemName()));
            inventory.remove(i);
        }

        super.remove();
    }

    // ------- STATIC METHODS -------

    public static int calculateDamage(int baseDmg, LivingGameEntity attacker, LivingGameEntity defender) {
        return baseDmg + attacker.getAttack() - defender.getDefense();
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

    public int getAttackRange() {
        return attackRange;
    }

}
