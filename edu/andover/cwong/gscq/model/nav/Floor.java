package edu.andover.cwong.gscq.model.nav;

import edu.andover.cwong.gscq.model.unit.GameEntity;

public class Floor {
	private final Tile[][] floorTiles;
	private final GameEntity[][] units;
	public Floor(int x, int y) {
		floorTiles = new Tile[x][y];
		units = new GameEntity[x][y];
	}
	public Tile getTile(int x,int y) {
		return floorTiles[x][y];
	}
	public int getWidth() {
		return floorTiles[0].length;
	}
	public int getHeight() {
		return floorTiles.length;
	}
	
}
