package entity;

import java.awt.Point;
import java.util.ArrayList;

import control.Audio;
import control.Controller;
import pathfinding.Node;
import pickups.HpUp;
import world.Floor;
import world.World.TILE;
import gameobject.GameObject;
import gameobject.Mask;

/**
 *	Classe pour les monstres.
 */
public class Monster extends Entity {

	/**
	 * Porté de la vision du Monstre
	 */
	public static int RANGE = 20;
	/**
	 * Nom du monstre (Non utilisé dans le jeux)
	 */
	private String name;
	
	/* Pathfinding */
	/**
	 * Vrai si le monstre a vue le joueur et le suis
	 */
	private boolean searching;
	/**
	 * Dernière position ou il a vue le joueur.
	 */
	private Point lastTarget;
	/**
	 * totalPath
	 * 	Chemin jusqu'au joueur
	 * nextNode
	 * 	Prochain objectif pour le chemin
	 */
	private Node totalPath, nextNode;
	/**
	 * Latence de 1/6 de seconde avant que le monstre ne cherche de nouveau le joueur
	 * au lieu de 1/60 comme le déroulement du jeux.
	 */
	private int tickTillAI;
	
	/* Movement */
	/**
	 * Vitesse du monstre
	 */
	private float speed;
	/**
	 * Dégàt du monstre
	 */
	private int damage;
	/**
	 * Vitesse d'attaque du monstre
	 */
	private int attackSpeed;
	
	/**
	 * Limite avant de pouvoir attaqué a nouveau (déterminer par attackSpeed)
	 */
	private int cooldown;
	
	/**
	 * Fréquence à laquel le monstre émet son bruit.
	 */
	private int tickTillSound;
	
	/**
	 * Constructeur de monstre
	 * @param x
	 * 	X d'origine
	 * @param y
	 * 	Y d'origine
	 * @param health
	 * 	Vie d'origine (Et donx maximal)
	 * @param damage
	 * 	Dégat du monstre
	 * @param attackspeed
	 * 	Vitesse d'attaque
	 * @param speed
	 * 	Vitesse de déplacement
	 */
	public Monster(double x, double y,int health, int damage, int attackspeed, float speed) {
		super(x, y, health);
		
		this.damage = damage;
		this.attackSpeed = attackspeed;
		this.speed = speed;
		
		searching = false;
		lastTarget = new Point(-1, -1);
		totalPath = null;

		mask = new Mask(0.1875, x, y);
		
		cooldown = 0;
		tickTillSound = 0;
		tickTillAI = 0;
	}

	@Override
	public void update(Floor floor) {
		if (health <= 0) {
			destroy = true;
			if((int)(Math.random()*100) <= 3)
				dropLoot();
			
			if(sprite.contains("zombie"))
				Audio.play("zombie_death", 0.40);
			else
				Audio.play("spider_death" + Integer.toString(((int)(Math.random()*3+1))), 0.40);
		}

		if (seePlayer(floor)) {
			searching = true;
			lastTarget = new Point((int) Controller.get().getPlayer().getX(), (int) Controller.get().getPlayer().getY());
			
			if (tickTillAI == 0) {
				totalPath = pathfinding.Pathfinding.getPath(new Point((int) this.x, (int) this.y), lastTarget, floor);
				tickTillAI = 10;
			}
			
			nextNode = totalPath.getFirst();
			angle = Math.atan2(Controller.get().getPlayer().getY() - y, Controller.get().getPlayer().getX() - x);
			moveTo();
		} 
		else if (this.searching) {
			if (new Point((int) this.x, (int) this.y).distance(lastTarget) <= 0.1)
				searching = false;
			else if (new Point((int) this.x, (int) this.y).equals(nextNode.getCoor()))
				nextNode = totalPath.getDestructiveFirst();
			else
				moveTo();
		}

		if (Math.sqrt(Math.pow(x - floor.getPlayer().getX(), 2) + Math.pow(y - floor.getPlayer().getY(), 2)) < 0.6 && cooldown == 0) {
			floor.getPlayer().setHealth(floor.getPlayer().getHealth() - damage);
			if (floor.getPlayer().getHealth() > 0 && tickTillSound == 0) {
				if(sprite.equals("img/zombie.png"))
					Audio.play("zombie_attack" + Integer.toString(((int)(Math.random()*3+1))));
				else
					Audio.play("spider_attack" + Integer.toString(((int)(Math.random()*3+1))));
				tickTillSound = 20;
			}
			cooldown = attackSpeed;
		}
		
		if (cooldown > 0)
			cooldown --;
		if (tickTillSound > 0)
			tickTillSound--;
		if (tickTillAI > 0)
			tickTillAI = 0;

	}

	/**
	 * Fais que le monstre lache une caisse de vie par terre.
	 */
	private void dropLoot() {
		Controller.get().getObjectsToLoad().add(new HpUp(x, y));
	}

	/**
	 * Retourne le nom du Monstre
	 * @return
	 * 	Le nom du Monstre
	 * 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Change le nom du Monstre
	 * @param chooseName
	 * 	Le nouveau nom.
	 */
	public void setName(String chooseName) {
		name = chooseName;
	}
	/**
	 * 	Détermine si le Monstre vois le joueur.
	 * @return
	 * 	Vrai si le Monstre vois le joueur
	 */
	private boolean seePlayer(Floor floor) {
		Point player = new Point((int) floor.getPlayer().getX(), (int) floor
				.getPlayer().getY());

		if (Math.sqrt(Math.pow(floor.getPlayer().getX() - x, 2)
				+ Math.pow(floor.getPlayer().getY() - y, 2)) > RANGE)
			return false;

		double m = (Controller.get().getPlayer().getY() - y)
				/ (Controller.get().getPlayer().getX() - x);
		double b = y - m * x;

		if (x <= (Controller.get().getPlayer().getX())) {
			for (double i = x; i < Controller.get().getPlayer().getX(); i = i + 0.1)
				if (floor.getTiles()[(int) i][(int) (m * i + b)] == TILE.WALL
						|| floor.getTiles()[(int) i][(int) (m * i + b)] == TILE.ROCK)
					return false;
		} else {
			for (double i = x; i > Controller.get().getPlayer().getX(); i = i - 0.1)
				if (floor.getTiles()[(int) i][(int) (m * i + b)] == TILE.WALL
						|| floor.getTiles()[(int) i][(int) (m * i + b)] == TILE.ROCK)
					return false;
		}
		return true;
	}

	/**
	 * Déplace le monstre s'il n'y a pas de collision
	 * @param vx
	 * 	Vitesse en X
	 * @param vy
	 * 	Vitesse en Y
	 */
	private void move(double vx, double vy) {
		boolean hCheckW = false;
		boolean vCheckW = false;
		boolean hCheckE = false;
		boolean vCheckE = false;

		ArrayList<Mask> walls = Controller.get().getWorld().getFloor()
				.getWalls();
		for (Mask wall : walls) {
			mask.setX(x + vx);
			mask.setY(y + vy);
			if (Mask.collide(mask, wall)) {
				mask.setY(y);
				if (Mask.collide(mask, wall))
					hCheckW = true;
				mask.setX(x);
				mask.setY(y + vy);
				if (Mask.collide(mask, wall))
					vCheckW = true;
			}
		}

		
		for(GameObject o : Controller.get().getWorld().getFloor().getObjects()) {
			if(o != this && o instanceof Monster){
				
				double dist = Math.sqrt(Math.pow(o.getX() - x,2) + Math.pow(o.getY() - y,2));
				double limitDist = Math.sqrt(Math.pow(o.getMask().getW()/2 - this.mask.getW()/2 ,2) + Math.pow(o.getMask().getH()/2 - this.mask.getH()/2 ,2));
				if(dist < (limitDist)){
					double angle = Math.atan2(o.getY() - y, o.getX() - x);
					double dp = limitDist - dist;
					double dvx = dp * Math.cos(angle) + 0.01;
					double dvy = dp * Math.sin(angle) + 0.01;

					System.out.println(true);

					((Monster) o).move(dvx, dvy);
				}

			}
		}

		
		for(GameObject o : Controller.get().getWorld().getFloor().getObjects()) {
			if(o != this){
				mask.setX(x+vx);
				mask.setY(y+vy);
				if (Mask.collide(mask, o.getMask())) {
					mask.setY(y);
					if (Mask.collide(mask, o.getMask()))
						hCheckE = true;
					mask.setX(x);
					mask.setY(y + vy);
					if (Mask.collide(mask, o.getMask()))
						vCheckE = true;
				}
			}
		}

		if (!hCheckW && !hCheckE)
			x += vx;
		if (!vCheckW && !vCheckE)
			y += vy;
	}

	/**
	 * Déplace le monstre selon son angle avec ça cible.
	 */
	private void moveTo() {
		double angle = getMonsTargetAngle();
		move(speed * Math.cos(angle), speed * Math.sin(angle));
	}

	/**
	 * Retourne l'angle entre le monstre et sa cible
	 * @return
	 * 	L'angle avec sa cible.
	 */
	private double getMonsTargetAngle() {
		return Math.atan2(nextNode.getCoor().getY() - y + 0.5, nextNode.getCoor().getX() - x + 0.5);
	}

	

}
