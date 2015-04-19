package entity;

import java.util.ArrayList;

import world.Floor;
import gameObjects.GameObjects;
import gameObjects.Items;
import gameObjects.Weapons;

public abstract class Entity extends GameObjects {

	private int level, health, intellect, strenght;


	protected int agility;


	private int mana;


	private int speed;


	private int perception;
	

	private ArrayList<Items> inventory;

	public Entity(double x, double y, int health, ArrayList<Items> inventory) {
		super(x, y);
		this.health = health;
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
