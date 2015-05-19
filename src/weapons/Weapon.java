package weapons;

import gameobject.Item;
import world.Floor;

/**
 * Classe générique représentent un objet.
 */
public abstract class Weapon extends Item {
	/**
	 * Dégat des projectiles tirée
	 */
	protected int damage;
	/**
	 * Fréquence d'attaque
	 */
	protected int attackspeed;
	/**
	 * Temps avant de pouvoir réattaquer
	 */
	protected int cooldown;

	/**
	 * Constructeur générique d'arme
	 * 
	 * @param x
	 *            X d'origine
	 * @param y
	 *            Y d'origine
	 * @param damage
	 *            Dégat de l'arme
	 * @param attackspeed
	 *            Vitesse de l'arme
	 */
	public Weapon(double x, double y, int damage, int attackspeed) {
		super(x, y);
		this.damage = damage;
		this.attackspeed = attackspeed;
		cooldown = 0;
	}

	/**
	 * Méthode générique d'attaque
	 * 
	 * @param floor
	 *            Étage courant
	 */
	public abstract void attack(Floor floor);

	public void update() {
		if (cooldown > 0)
			cooldown--;
	}
}
