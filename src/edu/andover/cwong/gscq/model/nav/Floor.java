package edu.andover.cwong.gscq.model.nav;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.GameEntity;
import edu.andover.cwong.gscq.model.unit.LivingGameEntity;

// Represents a "grid" of terrain types and units on the floor.
public class Floor {
    public static final Tile WALL = new Tile(0);
    public Tile[][] floorTiles;
    public GameEntity[][] units;

    
    public Floor(int x, int y) {
        floorTiles = new Tile[y][x];
        units = new GameEntity[y][x];
    }

    // if not inside the floor, return a wall tile
    public Tile getTile(int x, int y) {
        if (x < 0 || y < 0 ||
            x >= floorTiles[0].length || y >= floorTiles.length) {
            return WALL;
        }
        Tile result = floorTiles[y][x];
        return result;
    }
    
    public GameEntity getEntity(int x, int y){
        if (x < 0 || y < 0 ||
                x >= floorTiles[0].length || y >= floorTiles.length) {
                return null;
            }
            GameEntity result = units[y][x];
            return result;
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
    public void setTile(int x, int y, Tile tile){
    	floorTiles[y][x]=tile;
    }

    public void unitHasMoved(LivingGameEntity lge, int xLoc, int yLoc) {
    	if (units[yLoc][xLoc] != null) {
    		units[yLoc][xLoc].dealWithCollision(lge);
    	} 
    	if (units[yLoc][xLoc] != null) {
    		return;
    	} else {
    		units[lge.getLastYLocation()][lge.getLastXLocation()] = null;
    		units[yLoc][xLoc] = lge;
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
        // make the array larger
        if (rowsConstructed >= floorTiles.length) {
            Tile[][] n_tiles = new Tile[floorTiles.length+1][row.length];
            System.arraycopy(floorTiles, 0, n_tiles, 0, floorTiles.length);
            GameEntity[][] n_ge =
                    new GameEntity[floorTiles.length+1][row.length];
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
    
    
    
    
    
    
    // ----------- FLOOR GENERATION -----------
    
    
    public static int[][] floorTilesCreator;
    
    private static ArrayList<Room> roomsOnFloor;
    
    public static int[][] createFloor(int width, int height) {
    	
    	floorTilesCreator = new int[width][height];
    	
    	roomsOnFloor = new ArrayList<Room>();
    	
    	// Set all tiles equal to 0, with -1 forming the perimeter.
    	
    	for (int i = 0; i < height; i++) {
    		for (int j = 0; j < width; j++) {
    			if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
    				floorTilesCreator[i][j] = -1;
    			} else {
    				floorTilesCreator[i][j] = 0;
    			}
    		}
    	}
    	
    	// Generates the proper amount of rooms for this floor.
    	
    	int roomsPerFloor = (int) (1.0 * width * height / Room.PRIORITY_AREA / 4);
    	
    	while (roomsPerFloor > 0) {
    		if (generateRoom(new Room(
    				(int) (Math.random() * (width - 2)) + 1,
    				(int) (Math.random() * (height - 2)) + 1))) {
    			roomsPerFloor--;
    		}
    	}
    	
    	// Generates paths between the rooms.
    	generatePaths();
    	
    	
    	// If all rooms aren't connected, try again.
    	for (int i = 0; i < roomsOnFloor.size(); i++) { 
    		if (!roomsOnFloor.get(i).isConnected()) {
    			createFloor(floorTilesCreator[0].length, floorTilesCreator.length);
    			break;
    		}
    	}
    	
    	// Returns the floor.
    	return floorTilesCreator;
    }
    
    
    public static boolean generateRoom(Room room) {
    	
    	// Ensures the given room is entirely within the floor bounds.
    	for (int i = room.getTLTY() - 1; i < room.getBLTY() + 1; i++) {
    		for (int j = room.getTLTX() - 1; j < room.getTRTX() + 1; j++) {
    			if (floorTilesCreator[i][j] != 0) {
    				return false;
    			}
    		}
    	}
    	
    	// Ensures the edge of each room is sufficiently far
    	// from the edges of the floor.
    	if (room.getTRTX() + 3 > floorTilesCreator[0].length ||
    		room.getBLTY() + 3 > floorTilesCreator.length) {
    		return false;
    	}
    	
    	// Adds the room to the floor for later reference.
    	roomsOnFloor.add(room);
    	
    	// Generates the tile values within the room.
    	for (int i = room.getTLTY() - 1; i < room.getBLTY() + 1; i++) {
    		for (int j = room.getTLTX() - 1; j < room.getTRTX() + 1; j++) {
    			if (i == room.getTLTY() - 1 || i == room.getBLTY()||
    					j == room.getTLTX() - 1 || j == room.getTRTX()) {
    				// Thanks to Phrip Lamkin for assisting in fixing a bug here.
    				floorTilesCreator[i][j] = -1;
    			} else {
    				floorTilesCreator[i][j] = 2;
    			}
    		}
    	}
    	
    	// Generates a 'beacon point,' which is used for rooms
    	// to find and connect to one another.
    	generateBeacon(room);
    	
    	return true;
    }
    
    private static void generateBeacon(Room room) {
    	
    	// Select a wall for the beacon to generate on.
    	boolean validWall;
    	int wall;
    	
    	// Ensure the wall is far enough from the edge of the floor for
    	// path generation.
    	do {
    		validWall = true;
    		// 1 is up
    		// 2 is right
    		// 3 is down
    		// 4 is left    		
    		wall = (int) (Math.random() * 4 + 1);
    		
    		if (wall == 1) {
    			if (room.getTLTY() - 4 < 0) {
    				validWall = false;
    			}
    		} else if (wall == 2) {
    			if (room.getTRTX() + 3 > floorTilesCreator[0].length) {
    				validWall = false;
    			}
    		} else if (wall == 3) {
    			if (room.getBLTY() + 3 > floorTilesCreator.length) {
    				validWall = false;
    			}
    		} else if (wall == 4) {
    			if (room.getTLTX() - 4 < 0) {
    				validWall = false;
    			}
    		} else {
    			throw new IllegalArgumentException("WTF Math.random()???");
    		}
    	
    	} while (!validWall);
    	
    	// Choose a point on the chosen wall to be the 'beacon point,'
    	// and adjusts the map and notifies the room of the beacon location.
    	int startCoord;
    	if (wall % 2 == 0) {
    		startCoord = (int) (Math.random() * room.getHeight()) + room.getTLTY();
    		if (wall == 2) {
    			floorTilesCreator[startCoord][room.getTRTX() + 0] = 2;
    			floorTilesCreator[startCoord][room.getTRTX() + 1] = 5;
    			room.setBeaconY(startCoord);
    			room.setBeaconX(room.getTRTX() + 1);
    		} else {
    			floorTilesCreator[startCoord][room.getTLTX() - 1] = 2;
    			floorTilesCreator[startCoord][room.getTLTX() - 2] = 5;
    			room.setBeaconY(startCoord);
    			room.setBeaconX(room.getTLTX() - 2);
    		}
    	} else {
    		startCoord = (int) (Math.random() * room.getWidth()) + room.getTLTX();
    		if (wall == 1) {
    			floorTilesCreator[room.getTRTY() - 1][startCoord] = 2;
    			floorTilesCreator[room.getTRTY() - 2][startCoord] = 5;
    			room.setBeaconX(startCoord);
    			room.setBeaconY(room.getTRTY() - 2);
    		} else {
    			floorTilesCreator[room.getBLTY() + 0][startCoord] = 2;
    			floorTilesCreator[room.getBLTY() + 1][startCoord] = 5;
    			room.setBeaconX(startCoord);
    			room.setBeaconY(room.getBLTY() + 1);
    		}
    	}
    }
    
    private static void generatePaths() {
    	
    	try {
    	int beaconX;
    	int beaconY;
    	roomsOnFloor.get(0).connectRoom();
    	
    	// Choose a beacon to start from.
    	for (int a = 0; a < roomsOnFloor.size(); a++) {
    		int i = roomsOnFloor.get(a).getBeaconY();
    		int j = roomsOnFloor.get(a).getBeaconX();
    		
    		
    		// Find another beacon to connect to.
    		if (i < roomsOnFloor.size() - 1) {
    			beaconX = roomsOnFloor.get(i + 1).getBeaconX();
    			beaconY = roomsOnFloor.get(i + 1).getBeaconY();
    		} else {
    			beaconX = roomsOnFloor.get(0).getBeaconX();
    			beaconY = roomsOnFloor.get(0).getBeaconY();
    		}
    	
    		
   			// Generates a path between the two beacons. 
    		// If another path is encountered during generation,
    		// the path simply ends, connected to that path.
  			boolean arrived = false;
  				
  			int x = j;
  			int y = i;
  			int counter;
  			
  			while (!arrived) {
  				
  				counter = (int) (Math.random() * 15) + 10;
  				
  				while (x < beaconX) { 
  					x++;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
						floorTilesCreator[y][x] = 1;
					}
  					
  					counter--;
  					if (counter < 0) {
  						break;
  					}
  				}
  				while (x > beaconX) {
  					x--;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
  						floorTilesCreator[y][x] = 1;
  					}
  					
  					counter--;
  					if (counter < 0) {
  						break;
  					}
  				}
  				
  				counter = (int) (Math.random() * 15) + 10;
  				
  				while (y > beaconY) {
  					y--;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
  						floorTilesCreator[y][x] = 1;
  					}
  					
  					counter--;
  					if (counter < 0) {
  						break;
  					}
  				}
  				while (y < beaconY) {
  					y++;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
  						floorTilesCreator[y][x] = 1;
  					}
  					
  					counter--;
  					if (counter < 0) {
  						break;
  					}
  				}
  				
  				if (beaconX == x && beaconY == y) {
  					arrived = true;
  				}
  				
  			}
  			
  			// If the path is connected to the main body of rooms,
  			// adjust the map to reflect this connection.
  			if (floorTilesCreator[beaconY][beaconX] == 6) {
  				connectTiles(beaconX, beaconY);
  			}
  			if (roomsOnFloor.get(a).isConnected()) {
  				connectTiles(j, i);
  			}
  						
  		}
    	} catch (ArrayIndexOutOfBoundsException e) {
    		createFloor(floorTilesCreator[0].length, floorTilesCreator.length);
    		return;
    	}
    	
    	
    }
 
    
    private static void connectTiles(int x, int y) {
    	
    	// Change the status of the selected tile
    	// to connected.
    	
    	if (floorTilesCreator[y][x] == 1) {
    		floorTilesCreator[y][x] = 6;
    	} else if (floorTilesCreator[y][x] == 5) {
    		
    		floorTilesCreator[y][x] = 6;
    		
    		for (int a = 0; a < roomsOnFloor.size(); a++) {
    			if (roomsOnFloor.get(a).getBeaconX() == x
    					&& roomsOnFloor.get(a).getBeaconY() == y) {
    				roomsOnFloor.get(a).connectRoom();
    			}
    		}
    	} else if (floorTilesCreator[y][x] != 6){
    		return;
    	}
    	
    	// Change the status of surrounding tiles, if they are
    	// paths.
    	if (floorTilesCreator[y - 1][x] == 1
    			|| floorTilesCreator[y - 1][x] == 5) {
    		connectTiles(x, y - 1);
    	}
    	if (floorTilesCreator[y + 1][x] == 1
    			|| floorTilesCreator[y + 1][x] == 5) {
    		connectTiles(x, y + 1);
    	}
    	if (floorTilesCreator[y][x - 1] == 1
    			|| floorTilesCreator[y][x - 1] == 5) {
    			connectTiles(x - 1, y);
    	}
    	if (floorTilesCreator[y][x + 1] == 1
    			|| floorTilesCreator[y][x + 1] == 5) {
    		connectTiles(x + 1, y);
    	}
    	
    }
}
