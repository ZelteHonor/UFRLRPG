package gameObjects;

import world.Floor;

/**
 * @author gabriel
 *	Classe représentent une arme a distance
 */
public class Ranges extends Weapons {

	private int speed;
	
	/** 
	 * Constructeur d'arme à distance
	 * @param weight
	 * 	Poids de l'arme
	 * @param evil
	 * 	S'il est maudit
	 * @param range
	 * 	La porté de ses projectiles
	 * @param damage
	 * 	Les dégats de ses projectiles
	 * @param cooldown
	 * 	Le temps à attendre entre chaque attaque
	 * @param speed
	 * 	Vitesse de ses projectiles
	 */
	public Ranges(int weight, boolean evil, int range, int damage, int cooldown, int speed) {
		super(weight, evil, range, damage, cooldown);
		this.speed = speed;
	}

	@Override
	public void attack(Floor floor) {
		Projectile proj = new Projectile(this.damage, this.range, speed * Math.cos(angle), speed * Math.sin(angle), this.angle);
		floor.getObjects().add(proj); //Crée un projectile et l'ajoute dans la liste des objets à updater.
	}
}
