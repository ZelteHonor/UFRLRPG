package gameObjects;

public class Ranges extends Weapons {

	private int speed;
	
	public Ranges(int weight, boolean evil, int range, int damage, int cooldown, int speed) {
		super(weight, evil, range, damage, cooldown);
		this.speed = speed;
	}

	@Override
	protected void attack() {
		// TODO Auto-generated method stub
		
	}

}
