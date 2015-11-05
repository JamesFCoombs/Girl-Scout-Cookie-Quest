package edu.andover.cwong.gscq.model.nav;

import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.LivingGameEntity;

// Represents a "grid" of terrain types and units on the floor.
public class Floor {
    private Tile[][] floorTiles;
    private GameEntity[][] units;

    // can we get rid of this ASAP
    public Floor(int x, int y) {
        floorTiles = new Tile[y][x];
        units = new GameEntity[y][x];
    }

    public Tile getTile(int x, int y) {
        return floorTiles[y][x];
    }

    public int getWidth() {
        return floorTiles[0].length;
    }

    public int getHeight() {
        return floorTiles.length;
    }
    
    // Control what happens for a single "turn" (or "step") of the game. Only
    // called when the player takes an action.
    public void step() {
        for (GameEntity[] row : units) {
            for (GameEntity unit : row) {
            	if (unit != null) { unit.update(); }
            }
        }
    }

    public void unitHasMoved(LivingGameEntity lge, int xLoc, int yLoc) {
    	if (units[xLoc][yLoc] != null) {
    		units[xLoc][yLoc].dealWithCollision(lge);
    	} else {
    		units[lge.getLastXLocation()][lge.getLastYLocation()] = null;
    		units[xLoc][yLoc] = lge;
    	}
    }
    
    public void removeGameEntity(GameEntity ge) {
        if (units[ge.getYLoc()][ge.getXLoc()] != ge) {
            throw new IllegalArgumentException("This unit doesn't exist!");
        }
        units[ge.getYLoc()][ge.getXLoc()] = null;
    }

    public void addGameEntity(GameEntity ge) {
        if (units[ge.getYLoc()][ge.getXLoc()] != null) {
            throw new IllegalArgumentException(
                    "Can't spawn units on occupied square!");
        }
        units[ge.getYLoc()][ge.getXLoc()] = ge;
    }
    
    // this entire thing is so ugly i hate java
    
    private int rowsConstructed = 0;
    
    // Factory-private constructor
    private Floor(int[] row) {
        floorTiles = new Tile[row.length][row.length];
        units = new GameEntity[row.length][row.length];
        floorTiles[0] = Tile.convertInts(row);
        rowsConstructed = 1;
    }
    
    // Adds a row to the Floor currently being constructed
    private void addRow(int[] row) {
        if (row.length != floorTiles.length) {
            System.err.println("Invalid floor formatting: Must be rectangle");
            System.exit(-2);
        }
        // double the size of the array
        if (rowsConstructed >= floorTiles.length) {
            Tile[][] n_tiles = new Tile[floorTiles.length * 2][row.length];
            System.arraycopy(floorTiles, 0, n_tiles, 0, floorTiles.length);
            GameEntity[][] n_ge =
                    new GameEntity[floorTiles.length * 2][row.length];
            System.arraycopy(units, 0, n_ge, 0, floorTiles.length);
            this.floorTiles = n_tiles;
            this.units = n_ge;
        }
        floorTiles[rowsConstructed] = Tile.convertInts(row);
        rowsConstructed++;
    }
    
    // All of this ugliness is pretty much just factory code
    
    private static Floor construct = null;
    
    // Either creates a new Floor (which subsequent calls will add rows onto)
    // or adds a row onto the existing construction.
    public static void constructFloor(int[] row) {
        if (construct == null) {
            construct = new Floor(row);
            return;
        }
        construct.addRow(row);
    }
    
    // Return the completed floor plan. TODO get rid of trailing empty rows
    public static Floor getConstructed() {
        Floor result = construct;
        // get rid of the reference so we can construct a new floor
        construct = null;
        return result;
    }
}
