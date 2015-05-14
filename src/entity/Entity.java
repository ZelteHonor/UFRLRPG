package entity;

import world.Floor;
import gameobject.GameObject;

/**
 *	Classe abstraite pour les objets "vivant" (Monstre et Joueur)
 */
public abstract class Entity extends GameObject {

	/**
	 * Vie de l'entité
	 */
	protected int health;
	
	/**
	 * Vie maximal de l'entité
	 */
	public final int MAX_HEALTH;
	
	/**
	 * Constructeur pour les entité.
	 * @param x
	 * 	X d'origine
	 * @param y
	 * 	Y d'origine
	 * 
	 * @param health
	 * 	Vie d'origine (et donc maximal)
	 */
	public Entity(double x, double y, int health) {
		super(x, y);
		this.health = health;
		MAX_HEALTH = health;
		
	}
	
	/**
	 * Retourne la vie présente de l'entité
	 * @return
	 * 	Le nombre de point de vie
	 */
	public int getHealth() {
		return health;
	}

	/**
	 * Change la vie d'une entité
	 * @param health
	 * 	La nouvelle vie
	 */
	public void setHealth(int health) {

		if(health > MAX_HEALTH)
			health = MAX_HEALTH;
			
		this.health = health;
	}
	
	@Override
	public abstract void update(Floor floor);
}
