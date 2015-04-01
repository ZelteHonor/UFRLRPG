package gameObjects;

import entity.Entity;
import world.Floor;

/**
 * @author gabriel 
 * Classe représentant un projectile
 */
public class Projectile extends GameObjects {
	private int damage, distance;
	double vx, vy;

	/**
	 * Constructeur de projectile
	 * 
	 * @param damage
	 *            Les dégats s'il touche une cible
	 * @param distance
	 *            Sa distance maximal avant de disparaitre
	 * @param vx
	 *            Sa vitesse en X
	 * @param vy
	 *            Sa vitesse en Y
	 * @param angle
	 *            L'angle vers laquel il est tournée.
	 */
	public Projectile(double x, double y, int damage, int distance, double vx, double vy,
			double angle) {
		super(x, y);
		this.damage = damage;
		this.distance = distance;
		this.vx = vx;
		this.vy = vy;
		this.angle = angle;
	}

	@Override
	public void update(Floor floor) {
		boolean exist = true;
		for (GameObjects o : floor.getObjects()) { // Regarde s'il touche une
													// entité
			if (o instanceof Entity && exist
					&& Mask.collide(this.mask, o.getMask())) {
				((Entity) o).setHealth(((Entity) o).getHealth() - damage);
				floor.getObjects().remove(this);
				exist = false;
			}
		}
		for (Mask m : floor.getWalls()) { //Regarde s'il touche un mur.
			if (exist && distance <= 0 || Mask.collide(this.mask, m)) {
				floor.getObjects().remove(this);
				exist = false;
			}
		}
		if (exist) { //Déplace le projectile selon sa vitesse
			this.setX(getX() + vx);
			this.setY(getY() + vy);
			distance--;
		}
	}
}