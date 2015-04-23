package gameObjects;

import world.Floor;

/**
 * @author gabriel
 *	Classe représentent une arme a distance
 */
public class Ranges extends Weapons {

	private float speed;
	
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
	 * @param d
	 * 	Vitesse de ses projectiles
	 */
	public Ranges(double x, double y, int weight, boolean evil, int range, int damage, int cooldown, float d) {
		super(x, y, weight, evil, range, damage, cooldown);
		this.speed = d;
	}

	@Override
	public void attack(Floor floor) {
		Projectile proj = new Projectile(x, y, damage, speed, floor.getPlayer().getAngle());
		proj.setSprite("img/arrow.png");
		floor.getObjects().add(proj); //Crée un projectile et l'ajoute dans la liste des objets à updater.
	}
}
