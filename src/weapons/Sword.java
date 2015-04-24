package weapons;

import world.Floor;

public class Sword extends Weapon{

	public Sword(double x, double y, int damage, int speed) {
		super(x, y, damage, speed);
		sprite = "img/sword.png";
	}

	@Override
	public void attack(Floor floor) {	
	}
}
