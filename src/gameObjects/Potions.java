package gameObjects;

public class Potions extends Items {

	private int lifeRegen;

	public Potions(int weight, boolean evil, int lifeRegen) {
		super(weight, evil);
		this.lifeRegen = lifeRegen;
	}

}
