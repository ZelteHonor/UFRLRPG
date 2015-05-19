package pickups;

import world.Floor;
import gameobject.Item;
import gameobject.Mask;

public abstract class PickUps extends Item {

	/**
	 * Constructeur
	 * 
	 * @param x
	 * @param y
	 */
	public PickUps(double x, double y) {
		super(x, y);
		mask = new Mask(0.10, x, y);
	}

	/**
	 * v�rifie si les conditions n�c�ssaires sont pr�sents pour que le joueur
	 * utilise l'objet
	 */
	public void update(Floor floor) {
		if ((int) x == (int) floor.getPlayer().getX()
				&& (int) y == (int) floor.getPlayer().getY()) {
			activateEffect(floor);
			this.destroy = true;
		}

	}

	/**
	 * Active l'�ffet propre au PickUp
	 * 
	 * @param floor
	 */
	protected abstract void activateEffect(Floor floor);

}
