package entity;

import java.util.ArrayList;

import gameObjects.Items;

public class Player extends Entity{

	public Player(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception,ArrayList<Items> inventory) {
		super(level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		
	}

}
