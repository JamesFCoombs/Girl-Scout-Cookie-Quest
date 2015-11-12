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
    	
    	// Set all tiles equal to 0, with -1 forming the perimiter.
    	
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
    	
    	return floorTilesCreator;
    }
    
    
    public static boolean generateRoom(Room room) {
    	
    	for (int i = room.getTLTY() - 1; i < room.getBLTY() + 1; i++) {
    		for (int j = room.getTLTX() - 1; j < room.getTRTX() + 1; j++) {
    			if (floorTilesCreator[i][j] != 0) {
    				return false;
    			}
    		}
    	}
    	
    	if (room.getBLTX() + 3 > floorTilesCreator[0].length ||
    		room.getBLTY() + 3 > floorTilesCreator.length) {
    		return false;
    	}
    	
    	roomsOnFloor.add(room);
    	
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
    	
    	generateBeacon(room);
    	
    	return true;
    }
    
    private static void generateBeacon(Room room) {
    	// Select wall
    	boolean validWall;
    	int wall;
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
    	boolean validBeacon = false;
    	int beaconX;
    	int beaconY;
    	roomsOnFloor.get(0).connectRoom();
    	
    	for (int a = 0; a < roomsOnFloor.size(); a++) {
    		int i = roomsOnFloor.get(a).getBeaconY();
    		int j = roomsOnFloor.get(a).getBeaconX();
    		
    		validBeacon = false;
    		
    		// beaconX and beaconY search for a valid beacon
    		beaconX = j;
    		beaconY = i;
    		int counter = 0;
    		
    		// 1 is up, 2 is right, 3 is down, 4 is left
			int direction = 0;
			int wallDir = 0;
			
			// Move away from current room until a wall is hit 
			if (floorTilesCreator[beaconY][beaconX - 1] == 2) {
				while (floorTilesCreator[beaconY][beaconX + 1] == 0) {
					beaconX++;
				}
				wallDir = 2;
				direction = 1;
			} else if (floorTilesCreator[beaconY][beaconX + 1] == 2) {
				while (floorTilesCreator[beaconY][beaconX - 1] == 0) {
					beaconX--;
				}
				wallDir = 4;
				direction = 1;
			} else if (floorTilesCreator[beaconY - 1][beaconX] == 2) {
				while (floorTilesCreator[beaconY + 1][beaconX] == 0) {
					beaconY++;
				}
				wallDir = 3;
				direction = 2;
			} else if (floorTilesCreator[beaconY + 1][beaconX] == 2) {
				while (floorTilesCreator[beaconY - 1][beaconX] == 0) {
					beaconY--;
				}
				wallDir = 1;
				direction = 2;
			}
			
			int possibleLoc = validBeaconInVicinity(beaconX, beaconY, j, i);
			if (possibleLoc > 0) {
				beaconX = possibleLoc;
				validBeacon = true;
			} else if (possibleLoc < 0) {
				beaconY = - possibleLoc;
				validBeacon = true;
			}
    		
    		while (counter <= 50 && !validBeacon) {
    			
    			counter++;
    			
    			// CRAWLS UP WALL
    			while (!validBeacon && direction == 1) {
    				if (floorTilesCreator[beaconY - 1][beaconX] == -1) {
    					if (wallDir == 4) {
    						direction = 2;
    						wallDir = 1;
    					}
    					if (wallDir == 2) {
    						direction = 4;
    						wallDir = 1;
    					}
    				}
    				
    				beaconY--;
    				int validBeaconLocation = validBeaconInVicinity(beaconX, beaconY, j, i);
    				if (validBeaconLocation > 0) {
    					beaconX = validBeaconLocation;
    					validBeacon = true;
    				} else if (validBeaconLocation < 0) {
    					beaconY = -validBeaconLocation;
    					validBeacon = true;
    				}
    				
    				if (wallDir == 4) {
    					if (floorTilesCreator[beaconY][beaconX - 1] != -1) {
    						direction = 4;
    						wallDir = 3;
    					}
    				}
    				if (wallDir == 2) {
    					if (floorTilesCreator[beaconY][beaconX + 1] != -1) {
    						direction = 2;
    						wallDir = 3;
    					}
    				}
    			}
    			
    			// CRAWLS RIGHT ALONG WALL
    			while (!validBeacon && direction == 2) {
    				
    				if (floorTilesCreator[beaconY][beaconX + 1] == -1) {
    					if (wallDir == 1) {
    						direction = 3;
    						wallDir = 2;
    					}
    					if (wallDir == 3) {
    						direction = 1;
    						wallDir = 2;
    					}
    				}
    		
    				beaconX++;
    				int validBeaconLocation = validBeaconInVicinity(beaconX, beaconY, j, i);
    				if (validBeaconLocation > 0) {
    					beaconX = validBeaconLocation;
    					validBeacon = true;
    				} else if (validBeaconLocation < 0) {
    					beaconY = -validBeaconLocation;
    					validBeacon = true;
    				}
    				
    				if (wallDir == 1) {
    					if (floorTilesCreator[beaconY - 1][beaconX] != -1) {
    						direction = 1;
    						wallDir = 4;
    					}
    				}
    				if (wallDir == 3) {
    					if (floorTilesCreator[beaconY + 1][beaconX] != -1) {
    						direction = 3;
    						wallDir = 4;
    					}
    				}
    			}
    			
    			// CRAWLS DOWN ALONG WALL
    			while (!validBeacon && direction == 3) {
    				
    				if (
        					floorTilesCreator[beaconY + 1][beaconX] == -1) {
    					if (wallDir == 4) {
    						direction = 2;
    						wallDir = 3;
    					}
    					if (wallDir == 2) {
    						direction = 4;
    						wallDir = 3;
    					}
    				}
    				
    				beaconY++;
    				int validBeaconLocation = validBeaconInVicinity(beaconX, beaconY, j, i);
    				if (validBeaconLocation > 0) {
    					beaconX = validBeaconLocation;
    					validBeacon = true;
    				} else if (validBeaconLocation < 0) {
    					beaconY = -validBeaconLocation;
    					validBeacon = true;
    				}
    				
    				if (wallDir == 4) {
    					if (floorTilesCreator[beaconY][beaconX - 1] != -1) {
    						direction = 4;
    						wallDir = 1;
    					}
    				}
    				if (wallDir == 2) {
    					if (floorTilesCreator[beaconY][beaconX + 1] != -1) {
    						direction = 2;
    						wallDir = 1;
    					}
    				}
    			}
    					
    			// CRAWLS LEFT ALONG WALL
    			while (!validBeacon && direction == 4) {
    				
    				if (floorTilesCreator[beaconY][beaconX - 1] == -1) {
    					if (wallDir == 1) {
    						direction = 3;
    						wallDir = 4;
    					}
    					if (wallDir == 3) {
    						direction = 1;
    						wallDir = 4;
    					}
    				}
    				beaconX--;
    				int validBeaconLocation = validBeaconInVicinity(beaconX, beaconY, j, i);
    				if (validBeaconLocation > 0) {
    					beaconX = validBeaconLocation;
    					validBeacon = true;
    				} else if (validBeaconLocation < 0) {
    					beaconY = -validBeaconLocation;
    					validBeacon = true;
    				}
    				
    				if (wallDir == 1) {
    					if (floorTilesCreator[beaconY - 1][beaconX] != -1) {
    						direction = 1;
    						wallDir = 2;
    					}
    				}
    					if (wallDir == 3) {
    					if (floorTilesCreator[beaconY + 1][beaconX] != -1) {
    						direction = 3;
    						wallDir = 2;
    					}
    				}
    			}
    		}
    		
    		if (!validBeacon) {
    			createFloor(floorTilesCreator[0].length, floorTilesCreator.length);
    			return;
    		}
    		
   			// GENERATES PATH BETWEEN BEACONS
  				
  			boolean arrived = false;
  				
  			int x = j;
  			int y = i;
  			
  			while (!arrived) {
  				
  				while (x < beaconX) { 
  					x++;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
						floorTilesCreator[y][x] = 1;
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
  				}
  				
  				while (y > beaconY) {
  					y--;
  					if (floorTilesCreator[y][x] == 6
  							|| floorTilesCreator[y][x] == 1) {
  						beaconX = x;
  						beaconY = y;
  					} else if (floorTilesCreator[y][x] != 5) {
  						floorTilesCreator[y][x] = 1;
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
  				}
  				
  				if (beaconX == x && beaconY == y) {
  					arrived = true;
  				}
  				
  			}
  			
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
    
    // RETURNS 0 IF NO BEACON, POSITIVE NUMBER IF DIFFERENT X COORD, NEGATIVE NUMBER
    //                                       IF DIFFERENT Y COORD
    
    private static int validBeaconInVicinity(int beaconX, int beaconY, int origX, int origY) {
    	
    	for (int i = beaconX; i < floorTilesCreator.length; i++) {
    		if (i != origX && (floorTilesCreator[beaconY][i] == 1
    				|| floorTilesCreator[beaconY][i] == 5
    				|| floorTilesCreator[beaconY][i] == 6)) {
    			return i;
    		}
    		if (floorTilesCreator[beaconY][i] != 0) {
    			break;
    		}
    	}
    	
    	for (int i = beaconX; i > 0; i--) {
    		if (i != origX && (floorTilesCreator[beaconY][i] == 1
    				|| floorTilesCreator[beaconY][i] == 5
    				|| floorTilesCreator[beaconY][i] == 6)) {
    			return i;
    		}
    		if (floorTilesCreator[beaconY][i] != 0) {
    			break;
    		}
    	}
    	
    	for (int i = beaconY; i < floorTilesCreator[0].length; i++) {
    		if (i != origY && (floorTilesCreator[i][beaconX] == 1
    				|| floorTilesCreator[i][beaconX] == 5
    				|| floorTilesCreator[i][beaconX] == 6)) {
    			return -i;
    		}
    		if (floorTilesCreator[i][beaconX] != 0) {
    			break;
    		}
    	}
    	
    	for (int i = beaconY; i > 0; i--) {
    		if (i != origY && (floorTilesCreator[i][beaconX] == 1
    				|| floorTilesCreator[i][beaconX] == 5
    				|| floorTilesCreator[i][beaconX] == 6)) {
    			return -i;
    		}
    		if (floorTilesCreator[i][beaconX] != 0) {
    			break;
    		}
    	}
    	
    	return 0;
    }
    
    private static void connectTiles(int x, int y) {
    	
    	// Flips the tile in question.
    	
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
    	
    	// Flips surrounding tiles
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
