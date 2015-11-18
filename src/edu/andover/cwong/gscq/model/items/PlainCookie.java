package edu.andover.cwong.gscq.model.items;

public class PlainCookie extends CookieRecipe{

    public PlainCookie() {
        setItemID("PlainCookie");
        setCounter(0);
        setNumStepsToIncrement(1);
        setNumCookiesToAdd(1);
    }
    
    public String getDescription() {
        return "The default recipe, provides one cookie per step.";
    }
    

}
