package edu.andover.cwong.gscq.view;

import javafx.scene.image.Image;

import edu.andover.cwong.gscq.model.unit.GameEntity;

public class EntitySprite extends Sprite {
    public static final Sprite PLAYER_SPRITE = new Sprite(
            new Image("file:res/char.png"), 0, 0
    );
    
    private GameEntity entity;
    
    private EntitySprite(GameEntity ge) {
        super(null);
        this.entity = ge;
    }
    
    private void updatePosition() {
        
    }
}
