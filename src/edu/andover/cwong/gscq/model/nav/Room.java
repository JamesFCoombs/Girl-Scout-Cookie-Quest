package edu.andover.cwong.gscq.model.nav;

import edu.andover.cwong.gscq.model.unit.GameEntity;

public class Room {
    
    public final static int PRIORITY_AREA = 40;
    
    private int topLeftTileX;
    private int topLeftTileY;
    
    private int beaconXLocation;
    private int beaconYLocation;

    private int width;
    private int height;
    
    private boolean isConnected; 

    public Room(int topLeftTileX, int topLeftTileY) {
        this.topLeftTileX = topLeftTileX;
        this.topLeftTileY = topLeftTileY;
        generateRoom();        
        isConnected = false;
    }
    
    public boolean isInRoom(GameEntity ge) {
        
        if (ge.getXLoc() >= topLeftTileX 
                && ge.getXLoc() < topLeftTileX + width
                && ge.getYLoc() >= topLeftTileY
                && ge.getYLoc() < topLeftTileY + height) {
            return true;
        }
        return false;
    }
    
    private void generateRoom() {
        width = (int) (Math.random() * 9 + 4);
        height = (int) (1 + 1.0 * PRIORITY_AREA / width);
    }
    
    
    public void setBeaconX(int x) {
        beaconXLocation = x;
    }
    
    public void setBeaconY(int y) {
        beaconYLocation = y;
    }
    
    public int getBeaconX() {
        return beaconXLocation;
    }
    
    public int getBeaconY() {
        return beaconYLocation;
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
