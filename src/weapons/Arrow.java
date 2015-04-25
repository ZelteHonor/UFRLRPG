package weapons;

import control.Audio;
import entity.Entity;
import entity.Monster;
import gameobject.GameObject;
import gameobject.Mask;
import world.Floor;

/**
 * @author gabriel 
 * Classe représentant un projectile
 */
public class Arrow extends GameObject {
	private int damage;
	private double vx, vy;
	
	private boolean stuck;
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
		this.mask = new Mask(0.039, x + Math.cos(angle)*0.01, y + Math.sin(angle)*0.01);
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
				Audio.playSound(0);//TODO
				this.destroy = true;
			}
		}
		
		/* Mouvement */
		if (!stuck) {
			for (Mask m : floor.getWalls()) {
				if (Mask.collide(this.mask, m)) {
					stuck = true;
					Audio.playSound((int)((Math.random() * 3)));//TODO
					ticksToDeath = 120;
				}
			}
		
			x += vx;
			y += vy;
			
			mask.setPosition(x + Math.cos(angle)*0.01, y + Math.sin(angle)*0.01);
		}
		else {
			ticksToDeath--;
			if (ticksToDeath <= 0)
				destroy = true;
		}
	}
}