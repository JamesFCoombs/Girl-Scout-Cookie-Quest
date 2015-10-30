package edu.andover.cwong.gscq.model.nav;
public class Floor {
	private final Tile[][] floorTiles;
	public Floor(int x, int y){
		floorTiles = new Tile[x][y];
	}
	public Tile getTile(int x,int y){
		return floorTiles[x][y];
	}
	public int getWidth(){
		return floorTiles[0].length;
	}
	public int getHeight(){
		return floorTiles.length;
	}
	
}
