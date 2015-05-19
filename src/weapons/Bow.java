package weapons;

import control.Audio;
import world.Floor;

/**
 * @author gabriel Classe représentent une arme a distance
 */
public class Bow extends Weapon {

	/**
	 * Vitesse des projectiles tiré
	 */
	private float speed;

	/**
	 * Constructeur d'arme à distance
	 * 
	 * @param damage
	 *            Les dégats de ses projectiles
	 * @param cooldown
	 *            Le temps à attendre entre chaque attaque
	 * @param d
	 *            Vitesse de ses projectiles
	 */
	public Bow(double x, double y, int damage, int attackspeed, float speed) {
		super(x, y, damage, attackspeed);
		this.speed = speed;
		sprite = "img/bow.png";

	}

	@Override
	public void attack(Floor floor) {
		if (cooldown == 0) {
			floor.getObjects()
					.add(new Arrow(x, y, damage, speed, floor.getPlayer()
							.getAngle()));
			cooldown = attackspeed;
			Audio.play("bow_launch"
					+ Integer.toString(((int) (Math.random() * 3 + 1))));
		}
	}
}
