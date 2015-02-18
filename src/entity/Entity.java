package entity;

import gameObjects.GameObjects;

public class Entity extends GameObjects {

	private int level, health, intellect, strenght, agility, mana, speed, perception;
	public Entity(int level,int  health,int  intellect,int  strenght,int  agility, int mana,int  speed,int  perception)
	{
		this.level = level;
		this.health = health;
		this.intellect = intellect;
		this.strenght = strenght;
		this.agility = agility;
		this.mana = mana;
		this.speed = speed;
		this.perception = perception;
	}
}
