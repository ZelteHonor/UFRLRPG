package weapons;

import control.Audio;
import entity.Entity;
import entity.Monster;
import gameobject.GameObject;
import gameobject.Mask;
import world.Floor;

/**
 * Classe représentant une flèche
 * 
 * @author gabriel
 */
public class Arrow extends GameObject {
	/**
	 * Dégat du projectile
	 */
	private int damage;
	/**
	 * Vitesse en X et en Y du projectile
	 */
	private double vx, vy;

	/**
	 * Si le projectile est planté dans un mur
	 */
	private boolean stuck;
	/**
	 * Temps avant la destruction du projectile (quand il est dans le mur)
	 */
	private int ticksToDeath;

	/**
	 * Constructeur de projectile
	 * 
	 * @param damage
	 *            Les dégats s'il touche une cible
	 * @param vx
	 *            Sa vitesse en X
	 * @param vy
	 *            Sa vitesse en Y
	 * @param angle
	 *            L'angle vers laquel il est tournée.
	 */
	public Arrow(double x, double y, int damage, double speed, double angle) {
		super(x, y);
		this.mask = new Mask(0.039, x + Math.cos(angle) * 0.01, y
				+ Math.sin(angle) * 0.01);
		sprite = "img/arrow.png";

		this.damage = damage;

		this.vx = Math.cos(angle) * speed;
		this.vy = Math.sin(angle) * speed;
		this.angle = angle;
	}

	@Override
	public void update(Floor floor) {
		/* Monstres */
		for (GameObject o : floor.getObjects()) {
			if (o instanceof Monster && Mask.collide(this.mask, o.getMask())) {
				((Entity) o).setHealth(((Entity) o).getHealth() - damage);
				Audio.play("bow_hit"
						+ Integer.toString(((int) (Math.random() * 3 + 1))));
				this.destroy = true;
			}
		}

		/* Mouvement */
		if (!stuck) {
			for (Mask m : floor.getWalls()) {
				if (Mask.collide(this.mask, m)) {
					stuck = true;
					Audio.play("bow_hit"
							+ Integer.toString(((int) (Math.random() * 3 + 1))));
					ticksToDeath = 120;
				}
			}

			x += vx;
			y += vy;

			mask.setPosition(x + Math.cos(angle) * 0.01, y + Math.sin(angle)
					* 0.01);
		} else {
			ticksToDeath--;
			if (ticksToDeath <= 0)
				destroy = true;
		}
	}
}