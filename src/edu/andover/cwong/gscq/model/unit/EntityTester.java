package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Floor;

import java.util.Scanner;

public class EntityTester {
	
	private static Floor floor;
	
	public static void main(String[] args) {
		String str = "plainCookieRecipe";
		System.out.println(str.substring(str.length() - 6));
		/*
		floor = new Floor(40, 40);
		
		Player player = new Player(8, 8);
		Enemy enemy1 = new Enemy(3, 3);
		Enemy enemy2 = new Enemy(8, 7);
		ItemEntity badge = new ItemEntity(8, 9, "Sash");
		
		
		floor.addGameEntity(player);
		floor.addGameEntity(enemy1);
		floor.addGameEntity(enemy2);
		floor.addGameEntity(badge);
		
		player.setFloor(floor);
		enemy1.setFloor(floor);
		enemy2.setFloor(floor);
		badge.setFloor(floor);
		
		Scanner kbrd = new Scanner(System.in);
		for (int i = 0; i < 50; i++) {
			displayMap();
			int x = kbrd.nextInt();
			player.move(x);
			
			floor.step();
			
			System.out.println("HP: " + player.getCurHealth());
			
		}
		kbrd.close(); */
	}
	
	public static void displayMap() {
		
		for (int i = 0; i < floor.getHeight(); i++) {
			for (int j = 0; j < floor.getWidth(); j++) {
				
				if (Floor.floorTilesCreator[i][j] == 0
						|| Floor.floorTilesCreator[i][j] == -1) {
					System.out.print(".\t");
				}
				if (Floor.floorTilesCreator[i][j] == 1
						|| Floor.floorTilesCreator[i][j] == 5
						|| Floor.floorTilesCreator[i][j] == 2
						|| Floor.floorTilesCreator[i][j] == 6) {
					System.out.print("+\t");
				}
				
			}
			System.out.println();			
		}
	}
}