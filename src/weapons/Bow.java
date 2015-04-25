package weapons;

import control.Audio;
import world.Floor;

/**
 * @author gabriel
 *	Classe représentent une arme a distance
 */
public class Bow extends Weapon {

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
	public Bow(double x, double y, int damage, int attackspeed, float speed) {
		super(x, y, damage, attackspeed);
		this.speed = speed;
		sprite = "img/bow.png"; 
		
	}

	@Override
	public void attack(Floor floor) {
		if (cooldown == 0) {
			floor.getObjects().add(new Arrow(x, y, damage, speed, floor.getPlayer().getAngle()));
			cooldown = attackspeed;
			Audio.playSound(/*(int)((Math.random() * 3) + 3)*/"	bow_launch1.wav");//TODO
		}
	}
}
