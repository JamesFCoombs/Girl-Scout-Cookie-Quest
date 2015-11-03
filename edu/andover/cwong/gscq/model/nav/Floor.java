package edu.andover.cwong.gscq.model.nav;

import edu.andover.cwong.gscq.model.unit.GameEntity;

public class Floor {
    private final Tile[][] floorTiles;
    private final GameEntity[][] units;

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
    
    public void step() {
        for (GameEntity[] row : units) {
            for (GameEntity unit : row) {
                unit.update();
            }
        }
    }

    // SOME METHODS ADDED BY JAMES FOR GE'S SAKE

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
}
