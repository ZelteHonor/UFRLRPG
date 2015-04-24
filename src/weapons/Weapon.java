package weapons;

import gameobject.Item;
import world.Floor;

public abstract class Weapon extends Item{

	protected int range;
	protected int damage;
	private int cooldown;
	
	public Weapon(double x, double y, int weight, boolean evil, int range, int damage, int cooldown) {
		super(x, y, weight, evil);
		this.range = range;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	public abstract void attack(Floor floor);

}
