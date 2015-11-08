package edu.andover.cwong.gscq.model.nav;

public class Room {
	
	public final static int PRIORITY_AREA = 40;
	
	private int topLeftTileX;
	private int topLeftTileY;

	private int width;
	private int height;
	
	private boolean isConnected; 

	public Room(int topLeftTileX, int topLeftTileY) {
		this.topLeftTileX = topLeftTileX;
		this.topLeftTileY = topLeftTileY;
		generateRoom();		
		isConnected = false;
	}
	
	private void generateRoom() {
		width = (int) (Math.random() * 9 + 4);
		height = (int) (1 + 1.0 * PRIORITY_AREA / width);
	}
	
	public boolean isConnected() {
		return isConnected;
	}
	
	public void connectRoom() {
		isConnected = true;
	}
	
	public int getTLTX() {
		return topLeftTileX;
	}
	
	public int getTLTY() {
		return topLeftTileY;
	}
	
	public int getTRTX() {
		return topLeftTileX + width;
	}
	
	public int getTRTY() {
		return topLeftTileY;
	}
	
	public int getBLTX() {
		return topLeftTileX;
	}
	
	public int getBLTY() {
		return topLeftTileY + height;
	}
	
	public int getBRTX() {
		return topLeftTileX + width;
	}
	
	public int getBRTY() {
		return topLeftTileY + width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
}
