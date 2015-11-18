package edu.andover.cwong.gscq.view;

import javafx.scene.image.Image;

import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.Player;
import edu.andover.cwong.gscq.model.items.Badge;
import edu.andover.cwong.gscq.model.items.CookieRecipe;
import edu.andover.cwong.gscq.model.items.Lipstick;
import edu.andover.cwong.gscq.model.items.Mascara;
import edu.andover.cwong.gscq.model.unit.Enemy;
import edu.andover.cwong.gscq.model.unit.ItemEntity;

public class EntitySprite extends Sprite {
    public static final Sprite PLAYER_SPRITE = new Sprite(
            new Image("file:res/char.png"), 60, 70
    );
    public static final Sprite ENEMY_SPRITE = new Sprite(
            new Image("file:res/enemy.png"), 60, 70
    );
    public static final Sprite ITEM_SPRITE = new Sprite(
            new Image("file:res/item.png"), 64, 64
    );
    public static final Sprite RECIPE_SPRITE = new Sprite(
            new Image("file:res/recipe.png"), 64, 64
    );
    public static final Sprite LIPSTICK_SPRITE = new Sprite(
            new Image("file:res/lipistick.png"), 64, 64
    );
    public static final Sprite MASCARA_SPRITE = new Sprite(
            new Image("file:res/mascara.png"), 64, 64
    );
    public static final Sprite BADGE_SPRITE = new Sprite(
            new Image("file:res/badge.png"), 64, 64
    );
    
    private static Sprite determineSprite(GameEntity ge) {
        if (ge instanceof Player) { return PLAYER_SPRITE; }
        if (ge instanceof Enemy) { return ENEMY_SPRITE; }
        if (ge instanceof ItemEntity) { 
            if (ge.getItem() instanceof CookieRecipe){
                return RECIPE_SPRITE;
            } 
            if (ge.getItem() instanceof Mascara){
                return MASCARA_SPRITE;
            } 
            if (ge.getItem() instanceof Lipstick){
                return LIPSTICK_SPRITE;
            } 
            if (ge.getItem() instanceof Badge){
                return BADGE_SPRITE;
            }
            return ITEM_SPRITE; 
            }
        return null;
    }
    
    private GameEntity entity;
    
    public void refresh() {
        super.refresh();
    }
    
    public EntitySprite(GameEntity ge) {
        super(determineSprite(ge));
        this.entity = ge;
    }
    
    public boolean isInMap() {
        return this.entity.isInMap();
    }
    
    public void updatePosition() {
        // If the entity we're displaying is a Player, we don't need to check
        // anything, since we're always in the center of the screen.
        if (this.entity instanceof Player) {
            this.setTileLocation(
                    ViewConstants.TOP_LEFT_BLANKS,
                    ViewConstants.TOP_LEFT_BLANKS
            );
        }
        else {
            int xDist = entity.distToPlayerX();
            int yDist = entity.distToPlayerY();
            // The +-1 is because TOP_LEFT_BLANKS are for a 1-origin coordinate
            // system, but the map uses a 0-origin coordinate system
            if (xDist > -ViewConstants.TOP_LEFT_BLANKS-1 &&
                xDist < ViewConstants.BOT_RIGHT_BLANKS+1 &&
                yDist > -ViewConstants.TOP_LEFT_BLANKS-1 &&
                yDist < ViewConstants.BOT_RIGHT_BLANKS+1
            ) {
                int mapX = ViewConstants.TOP_LEFT_BLANKS + xDist;
                int mapY = ViewConstants.TOP_LEFT_BLANKS + yDist;
                this.setX(mapX * ViewConstants.TILE_DIMENSIONS);
                this.setY(mapY * ViewConstants.TILE_DIMENSIONS);
                this.setVisible(true);
            }
            else {
                this.setVisible(false);
            }
        }
    }
}
