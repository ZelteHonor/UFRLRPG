package entity;

import java.awt.Point;
import java.util.ArrayList;
import world.Floor;
import gameObjects.Items;

public class Monster extends Entity{
	
	private String name;
	private boolean searching;
	private Point lastTarget;

	public Monster(double x, double y, int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(x, y, level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		searching = false;
		lastTarget = new Point(-1, -1);
	}

	@Override
	public void update(Floor floor) {
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
