package gameobject;

public class Potion extends Item{

	private int lifeRegen;
	
	public Potion(double x, double y, int weight, boolean evil, int lifeRegen) {
		super(x, y, weight, evil);
		this.lifeRegen = lifeRegen;
	}

}
