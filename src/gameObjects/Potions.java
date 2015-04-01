package gameObjects;

public class Potions extends Items{

	private int lifeRegen;
	
	public Potions(double x, double y, int weight, boolean evil, int lifeRegen) {
		super(x, y, weight, evil);
		this.lifeRegen = lifeRegen;
	}

}
