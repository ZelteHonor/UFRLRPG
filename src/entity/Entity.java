package entity;

import java.util.ArrayList;

import weapons.Weapon;
import world.Floor;
import gameobject.GameObject;
import gameobject.Item;

public abstract class Entity extends GameObject {

	protected int health;
	
	public Entity(double x, double y, int health) {
		super(x, y);
		this.health = health;
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
