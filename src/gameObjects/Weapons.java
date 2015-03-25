package gameObjects;

public abstract class Weapons extends Items{

	private int range, damage ,cooldown;
	
	public Weapons(int weight, boolean evil, int range, int damage, int cooldown) {
		super(weight, evil);
		this.range = range;
		this.damage = damage;
		this.cooldown = cooldown;
	}
	
	protected abstract void attack();

}
