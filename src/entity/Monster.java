package entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.LinkedList;

import world.Floor;
import gameObjects.Items;

public class Monster extends Entity{
	
	private boolean searching;
	private Point lastTarget;

	public Monster(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		searching = false;
		lastTarget = new Point(-1, -1);
	}

	@Override
	public void update(Floor floor) {
		
		
	}
	
	private class ASearch{
		
		
	}
	

}
