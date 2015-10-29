package edu.andover.cwong.gscq.model;
public class Floor {
	private Tile[][] floorTiles;
	private int width;
	private int length;
	public Floor(int x, int y){
		floorTiles = new Tile[x][y];
		width=x;
		length=y;
	}
	public Tile getTile(int x,int y){
		return floorTiles[x][y];
	}
	public int getWitdth(){
		return width;
	}
	public int getLength(){
		return length;
	}
}
