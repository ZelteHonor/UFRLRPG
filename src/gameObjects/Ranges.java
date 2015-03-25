package gameObjects;

import world.Floor;

public class Ranges extends Weapons {

	private int speed;
	
	public Ranges(int weight, boolean evil, int range, int damage, int cooldown, int speed, int length) {
		super(weight, evil, range, damage, cooldown);
		this.speed = speed;
	}

	@Override
	public void attack(Floor floor) {
		Projectile proj = new Projectile(this.damage, this.range, speed * Math.cos(angle), speed * Math.sin(angle), this.angle);
		floor.getObjects().add(proj);
	}
}
