package weapons;

import gameobject.Item;
import world.Floor;

public abstract class Weapon extends Item{
	protected int damage;
	protected int attackspeed;
	protected int cooldown;
	
	public Weapon(double x, double y, int damage, int attackspeed) {
		super(x, y);
		this.damage = damage;
		this.attackspeed = attackspeed;
		cooldown = 0;
	}
	
	public abstract void attack(Floor floor);

	public void update() {
		if (cooldown > 0)
			cooldown--;
	}
}
