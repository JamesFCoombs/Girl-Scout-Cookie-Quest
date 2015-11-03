package edu.andover.cwong.gscq.model.nav;

import edu.andover.cwong.gscq.model.unit.GameEntity;

public class Floor {
	private final Tile[][] floorTiles;
	private final GameEntity[][] units;
	public Floor(int x, int y) {
		floorTiles = new Tile[y][x];
		units = new GameEntity[y][x];
	}
	public Tile getTile(int x,int y) {
		return floorTiles[y][x];
	}
	public int getWidth() {
		return floorTiles[0].length;
	}
	public int getHeight() {
		return floorTiles.length;
	}
	
	// SOME METHODS ADDED BY JAMES FOR GE'S SAKE
	
	public void removeGameEntity(GameEntity GE) {
		//Stuff
	}
	
}
