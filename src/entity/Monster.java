package entity;

import java.util.ArrayList;

import gameObjects.Items;

public class Monster extends Entity{

	public Monster(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
