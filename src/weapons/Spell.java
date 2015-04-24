package weapons;

import world.Floor;

public class Spell extends Weapon{

	public Spell(double x, double y, int damage, int attackspeed) {
		super(x, y, damage, attackspeed);
	}

	@Override
	public void attack(Floor floor) {
	}
}
