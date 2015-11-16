package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.items.Mascara;
import edu.andover.cwong.gscq.model.items.PlainCookie;

public class Player extends LivingGameEntity {
	
	public int cookieCount = 0;
	private ArrayList<CookieRecipe> cookieList;
	
	
    public Player(int xLoc, int yLoc) {
        super(xLoc, yLoc);
        
        initializeMaxHealth(10);
        setDefense(0);
        setAttack(0);
        setBaseAttack(6);
        
        cookieList = new ArrayList<CookieRecipe>();
        addCookie(new PlainCookie());
        inventory = new ArrayList<Item>();
        addItem1(new Mascara());
        //System.out.println(inventory);
        
        GameEntity.player = this;
    }
    
    public boolean addItem(Item item) {
    	//if (item is a cookieRecipe) {
    	//		addCookie(item)
    	//}
    	return super.addItem(item);
    }
    
    public boolean addCookie(CookieRecipe cookieRecipe) {
    	cookieList.add(cookieRecipe);
    	return true;
    }
    
    
    public boolean addItem1(Item item) {
    	inventory.add(item);
    	return true;
    }
    
    // This removes the player from the game.
    public void remove() {
    	System.out.println("End the game.");
    	super.remove();
    }
    
    // This makes the number of cookies a player has
    // increase every time the player moves.
    public boolean move(int direction) {
    	boolean moved = super.move(direction);
    	if (moved) {
			int bonusCookies = 0;
    		for (int i = 0; i < cookieList.size(); i++) {
            	bonusCookies += cookieList.get(i).cookieIncrease();
    		}
            setCookiesCount(bonusCookies);
            return true;
    	}
    	return false;
    }
    
    // This makes the number of cookies a player has
    // increase every time the player moves.
    public boolean openInventory(int input) {
        if (input == 5) {
        	System.out.println(inventory.get(0));
        	inventory.get(0).toggleEquip();
            setAttack(inventory.get(0).attackIncrease());
            setDefense(inventory.get(0).defenseIncrease());
        } else {
            throw new IllegalArgumentException(
                "Invalid direction for LGE movement");
        }
        return true;
    }
/*
    public boolean selectItem(int itemEntry) {
    	if (itemEntry == 6) {
    		Item firstItem = inventory.get(0);
            firstItem.toggleEquip();
    	} else if (itemEntry == 7) {
    		Item secondItem = inventory.get(1);
    		secondItem.toggleEquip();
    	} else if (itemEntry == 8) {
    		Item thirdItem = inventory.get(2);
    		thirdItem.toggleEquip();
    	} else {
    		throw new IllegalArgumentException(
    			"Invalid item selection");
    	}
    	return true;
    }
*/    
    // This resets the number of cookies the player has every
    // time the player should gain a cookie by movement. 
    public void setCookiesCount(int cookies) {
    	cookieCount += cookies;
    }
    
    public int getCookieCount() {
    	return cookieCount;
    }
    
    //The cookieList has all the cookies that the player currently has.
    public ArrayList<CookieRecipe> getCookieList() {
    	return cookieList;
    }
    
    public void removeCookies() {super.remove();
        for (int i = 0; i < cookieList.size(); i++) {
            getCurFloor().addGameEntity(new ItemEntity(
                    xLocation, yLocation, cookieList.get(i).getItemID()
            ));
            cookieList.remove(i);
        }
    }
}
