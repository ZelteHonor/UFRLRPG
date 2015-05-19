package gameobject;

import world.Floor;

/**
 * Classe générique pour les objets
 */
public abstract class Item extends GameObject {
	public Item(double x, double y) {
		super(x, y);
	}

	public abstract void update(Floor floor);
}
