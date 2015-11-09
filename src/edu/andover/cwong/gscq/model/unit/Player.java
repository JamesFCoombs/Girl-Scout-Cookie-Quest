package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Item;
import edu.andover.cwong.gscq.model.items.PlainCookie;

public class Player extends LivingGameEntity {
	public int cookieCount = 0;
	private ArrayList<CookieRecipe> cookieList;
    public Player(int xLoc, int yLoc) {
        super(xLoc, yLoc);
        initializeMaxHealth(5); //SHOULD BE TEN
        setDefense(0);
        setAttack(0);
        setBaseAttack(6);
        cookieList = new ArrayList<CookieRecipe>();
        CookieRecipe cookie1 = new PlainCookie();
        addCookie(cookie1);
    }
    public boolean addCookie(CookieRecipe cookieRecipe) {
    	cookieList.add(cookieRecipe);
    	return true;
    }
    
    public void remove() {
    	System.out.println("End the game.");
    	super.remove();
    }
    public boolean move(int direction) {
    	boolean moved = super.move(direction);
    	if (moved) {
			int bonusCookies = 0;
    		for (int i = 0; i < cookieList.size(); i++) {
            	bonusCookies += cookieList.get(i).cookieIncrease();
    		};
            setCookiesCount(bonusCookies);
            return true;
    	};
    	return false;
    }
    // Spawns the player unit. TODO
    public static Player init() {
        // TODO: fix this
        return new Player(5, 5);
    }
    
    public void setCookiesCount(int cookies) {
    	cookieCount += cookies;
    }
    
    public int getCookieCount() {
    	return cookieCount;
    }
    
    public ArrayList<CookieRecipe> getCookieList() {
    	return cookieList;
    }
    
    public void removeCookie() {
        super.remove();
        for (int i = 0; i < cookieList.size(); i++) {
            getCurFloor().addGameEntity(new ItemEntity(xLocation, yLocation, cookieList.get(i).getItemID()));
            cookieList.remove(i);
        }
    }
}
