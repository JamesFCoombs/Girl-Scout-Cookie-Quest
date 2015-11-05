package edu.andover.cwong.gscq.model.unit;

import edu.andover.cwong.gscq.model.nav.Floor;
import java.util.Scanner;

public class EntityTester {
	
	public static void main(String[] args) {
		Floor floor = new Floor(10, 10);
		
		Player player = new Player(8, 8);
		Enemy enemy1 = new Enemy(3, 3);
		Enemy enemy2 = new Enemy(8, 7);
		ItemEntity badge = new ItemEntity(8, 9, "Sash");
		
		enemy1.setPlayer(player);
		enemy2.setPlayer(player);
		//GameEntity badge = new ItemEntity(8, 8, "Badge");
		
		floor.addGameEntity(player);
		floor.addGameEntity(enemy1);
		floor.addGameEntity(enemy2);
		floor.addGameEntity(badge);
		
		player.setFloor(floor);
		enemy1.setFloor(floor);
		enemy2.setFloor(floor);
		badge.setFloor(floor);
		
		floor.step();
		
		System.out.println(player.getCurHealth());
		player.move(3);
		
		floor.step();
		
		System.out.println(player.getXLoc() + ", " + player.getYLoc());
		System.out.println(player.getInventory().get(0).getItemID());
		
		Scanner kbrd = new Scanner(System.in);
		for (int i = 0; i < 20; i++) {
			int x = kbrd.nextInt();
			player.move(x);
			
			floor.step();
			
			System.out.println(player.getCurHealth());
			System.out.println(player.getXLoc() + ", " + player.getYLoc());
		}
	}
	
}
