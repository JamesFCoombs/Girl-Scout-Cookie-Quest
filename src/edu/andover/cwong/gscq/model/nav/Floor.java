package edu.andover.cwong.gscq.model.nav;

import java.util.ArrayList;

import edu.andover.cwong.gscq.model.unit.*;

// Represents a "grid" of terrain types and units on the floor.
public class Floor {
	
	// Each tile represents one square on the map.
    public static final Tile WALL = new Tile(0);
    public static final Tile PATH = new Tile(1);
    public static final Tile ROOM = new Tile(2);
    public static final Tile SHOP = new Tile(3);
    public static final Tile EXIT = new Tile(4);
    
    // The grid that represents the map.
    private Tile[][] floorTiles;
    
    // A grid that represents each GameEntity's location
    // on the map.
    private GameEntity[][] units;

    
    public Floor(int x, int y) {
        floorTiles = new Tile[y][x];
        units = new GameEntity[y][x];
        generateFloor(x, y);
    }
    
    public Tile[][] getGrid() { return floorTiles; }
    public GameEntity[][] getUnits() { return units; }

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
    
    public ArrayList<Room> getRoomsOnFloor() {
    	return roomsOnFloor;
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

    public boolean unitHasMoved(LivingGameEntity lge, int xLoc, int yLoc) {
    	if (floorTiles[yLoc][xLoc] == WALL) {
    		return false;
    	}
    	if (units[yLoc][xLoc] != null) {
    		units[yLoc][xLoc].dealWithCollision(lge);
    	} 
    	if (units[yLoc][xLoc] == null) {
    		units[lge.getLastYLocation()][lge.getLastXLocation()] = null;
    		units[yLoc][xLoc] = lge;
    	}
    	return true;
    }
    
    public void removeGameEntity(GameEntity ge) {
        if (units[ge.getYLoc()][ge.getXLoc()] != ge) {
            throw new IllegalArgumentException("This unit doesn't exist!");
        }
        units[ge.getYLoc()][ge.getXLoc()] = null;
    }

    public boolean addGameEntity(GameEntity ge) {
        if (units[ge.getYLoc()][ge.getXLoc()] != null
        		|| floorTiles[ge.getYLoc()][ge.getXLoc()] == WALL) {
            return false;
        }
        units[ge.getYLoc()][ge.getXLoc()] = ge;
        ge.setFloor(this);
        return true;
    }
  
    public void generateFloor(int width, int height) {
    	
    	// Creates a new map for this floor.
    	units = new GameEntity[height][width];
    	floorTiles = new Tile[height][width];
    	
    	// Make a grid of ints for the map to cast from
    	createFloor(width, height);
    	
    	// Takes the generated int grid and applies it to the map.
    	for (int i = 0; i < floorTiles.length; i++) {
    		for (int j = 0; j < floorTiles[0].length; j++) {
    			if (floorTilesCreator[i][j] == 0) {
    				floorTiles[i][j] = WALL;
    			} else if (floorTilesCreator[i][j] == 1) {
    				floorTiles[i][j] = PATH;
    			} else if (floorTilesCreator[i][j] == 2) {
    				floorTiles[i][j] = ROOM;
    			} else {
    				throw new IllegalArgumentException("Floor Generation messed up.");
    			}
    		}
    	}
    	
    	// Determines which room the player spawns in, which
    	// room the shop spawns in, and which room the exit
    	// spawns in.
    	Room playerRoom;
    	Room shopRoom;
    	Room exitRoom;
    	
    	int spawnX;
    	int spawnY;
    	
    	playerRoom = roomsOnFloor.get((int) (Math.random() * roomsOnFloor.size()));
    	
    	do {
    		shopRoom = roomsOnFloor.get((int) (Math.random() * roomsOnFloor.size()));
    	} while (shopRoom == playerRoom);
    	
    	do {
    		exitRoom = roomsOnFloor.get((int) (Math.random() * roomsOnFloor.size()));
    	} while (exitRoom == playerRoom || exitRoom == shopRoom);
    	
    	// Spawns the player in the specified room.
    	spawnX = playerRoom.getTLTX() + 
    			((int) (Math.random() * playerRoom.getWidth()));
    	spawnY = playerRoom.getTLTY() +
    			((int) (Math.random() * playerRoom.getHeight()));
    	
    	units[spawnY][spawnX] = GameEntity.player;
    	GameEntity.player.setXLoc(spawnX);
    	GameEntity.player.setYLoc(spawnY);
    	
    	// Spawns the exit in the specified room.
    	spawnX = exitRoom.getTLTX() + 
    			((int) (Math.random() * exitRoom.getWidth()));
    	spawnY = exitRoom.getTLTY() +
    			((int) (Math.random() * exitRoom.getHeight()));
    	
    	floorTiles[spawnY][spawnX] = EXIT;
    	
    	// Spawns the shop in the specified room.
    	spawnX = (shopRoom.getTLTX() + shopRoom.getTRTX()) / 2;
    	spawnY = shopRoom.getTLTY();
    	
    	floorTiles[spawnY][spawnX] = SHOP;
    	
    	
    }
    
    // ----------- FLOOR GENERATION -----------
    
    
    public static int[][] floorTilesCreator;
    
    private static ArrayList<Room> roomsOnFloor;
    
    private static int[][] createFloor(int width, int height) {
    	
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
    	
    	// Clean up the tiles, removing behind the scenes numbers that
    	// were used for generation but should not be used for the real
    	// code.
    	cleanTiles();
    	
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
  				
  				counter = (int) (Math.random() * 10) + 5;
  				
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
    
    public static void cleanTiles() {
    	for (int a = 0; a < roomsOnFloor.size(); a++) {
    		Room room = roomsOnFloor.get(a);
    		for (int i = room.getTLTY(); i < room.getBLTY(); i++) {
        		for (int j = room.getTLTX(); j < room.getTRTX(); j++) {
        			floorTilesCreator[i][j] = 2;
        		}
        	}
    	}
    	
    	for (int i = 0; i < floorTilesCreator.length; i++) {
    		for (int j = 0; j < floorTilesCreator[0].length; j++) {
    			if (floorTilesCreator[i][j] == 6) {
    				floorTilesCreator[i][j] = 1;
    			}
    			if (floorTilesCreator[i][j] == -1) {
    				floorTilesCreator[i][j] = 0;
    			}
    		}
    	}
    }
}
