package edu.andover.cwong.gscq.model.nav;

public class Tile {
	private final int id;
	public Tile(int x){
		id=x;
	}
	public Tile(){
		id=1;
	}
	public int getID(){
		return id;
	}
}
