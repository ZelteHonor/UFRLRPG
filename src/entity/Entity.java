package entity;

import java.util.ArrayList;

import world.Floor;
import gameObjects.GameObjects;
import gameObjects.Items;

public abstract class Entity extends GameObjects {

	private int level, health, intellect, strenght;


	protected int agility;


	private int mana;


	private int speed;


	private int perception;
	

	private ArrayList<Items> inventory;

	public Entity(double x, double y, int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(x, y);
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
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
	@Override
	public abstract void update(Floor floor);
}
