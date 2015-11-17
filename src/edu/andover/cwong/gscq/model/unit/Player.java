package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.items.PlainCookie;

public class Player extends LivingGameEntity {
	
	// cookieCount keeps track of how many cookies the player has.
	public int cookieCount = 0;
	
	// The list of cookieRecipes the player has.
	private ArrayList<CookieRecipe> cookieList;
	
    public Player(int xLoc, int yLoc) {
        super(xLoc, yLoc);
        
        // The inital stats for the player.
        initializeMaxHealth(10);
        setDefense(0);
        setAttack(0);
        setBaseAttack(6);
        
        // The player starts with the CookieRecipe plainCookie.
        cookieList = new ArrayList<CookieRecipe>();
        addCookie(new PlainCookie());
        
        // Sets this player as the player all other classes refer to.
        GameEntity.player = this;
    }
    
    // Adds Item item to the player's inventory. If the item is a cookieRecipe,
    // it is also added to the cookieList.
    public boolean addItem(Item item) {
    	if (item instanceof CookieRecipe) {
    		addCookie((CookieRecipe) (item));
    	}
    	return super.addItem(item);
    }
    
    // Ads a cookieRecipe to the cookieList.
    public boolean addCookie(CookieRecipe cookieRecipe) {
    	cookieList.add(cookieRecipe);
    	return true;
    }
 
    // This removes the player from the game.
    public void remove() {
    	super.remove();
    }
    
    // Moves the player. If the player moved, increments cookieCount.
    public boolean move(int direction) {
    	boolean moved = super.move(direction);
    	if (moved) {
			int bonusCookies = 0;
    		for (int i = 0; i < cookieList.size(); i++) {
            	bonusCookies += cookieList.get(i).incrementCount();
    		}
            increaseCookiesCount(bonusCookies);
            return true;
    	}
    	return false;
    }
    
    // TODO FIX THIS
    // Equips the first item in the inventory.
    @Deprecated
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

    // Increases cookieCount by a specified amount.
    public void increaseCookiesCount(int cookies) {
    	cookieCount += cookies;
    }
    
    public int getCookieCount() {
    	return cookieCount;
    }
    
    //The cookieList has all the cookies that the player currently has.
    public ArrayList<CookieRecipe> getCookieList() {
    	return cookieList;
    }
    
    // Removes all cookieRecipes from cookieList
    public void removeCookies() {super.remove();
        for (int i = 0; i < cookieList.size(); i++) {
            getCurFloor().addGameEntity(new ItemEntity(
                    xLocation, yLocation, cookieList.get(i).getItemID()
            ));
            cookieList.remove(i);
        }
    }
}
