package edu.andover.cwong.gscq.model.unit;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.items.CookieRecipe;

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
    }
    public void remove() {
    	System.out.println("End the game.");
    	super.remove();
    }
    public boolean gainCookie(int direction) {
    	Boolean moved = super.move(direction);
    	if (moved = true) {
//			int bonusCookies = 0;
//          for (int i = 0; i < cookieList.size(); i++) {
//            	bonusCookies += cookieList.get(i).cookieIncrease();
//          };
            //setCookiesCount(getCookieCount() + bonusCookies);
            setCookiesCount(1);
    	};
    	System.out.println(getCookieCount());
    	return true;
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
}
