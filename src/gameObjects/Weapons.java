package gameObjects;

public class Weapons extends Items{

	private int range;
	private int damage;
	
	public Weapons(int weight, boolean evil, int range, int damage) {
		super(weight, evil);
		this.range = range;
		this.damage = damage;
	}

}
