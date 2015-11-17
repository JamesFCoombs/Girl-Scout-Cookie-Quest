package edu.andover.cwong.gscq.view;

import javafx.scene.image.Image;

import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.Player;
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
            new Image("file:res/0.png"), 64, 64
    );
    
    private static Sprite determineSprite(GameEntity ge) {
        if (ge instanceof Player) { return PLAYER_SPRITE; }
        if (ge instanceof Enemy) { return ENEMY_SPRITE; }
        if (ge instanceof ItemEntity) { return ITEM_SPRITE; }
        return null;
    }
    
    private GameEntity entity;
    
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
            if (xDist > -ViewConstants.BOT_RIGHT_BLANKS &&
                xDist < ViewConstants.TOP_LEFT_BLANKS &&
                yDist > -ViewConstants.TOP_LEFT_BLANKS &&
                yDist < ViewConstants.BOT_RIGHT_BLANKS
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
