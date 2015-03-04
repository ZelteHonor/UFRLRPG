package entity;

import java.util.ArrayList;

import world.Floor;
import gameObjects.GameObjects;
import gameObjects.Items;

public abstract class Entity extends GameObjects {

	private int level, health, intellect, strenght, agility, mana, speed,
			perception;
	private ArrayList<Items> inventory;

	public Entity(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		this.level = level;
		this.health = health;
		this.intellect = intellect;
		this.strenght = strenght;
		this.agility = agility;
		this.mana = mana;
		this.speed = speed;
		this.perception = perception;
		this.inventory = inventory;
	}

	@Override
	public abstract void update(Floor floor);
}
