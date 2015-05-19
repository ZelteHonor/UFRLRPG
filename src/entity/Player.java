package entity;

import java.util.ArrayList;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import weapons.Bow;
import weapons.Sword;
import weapons.Weapon;
import world.Floor;
import control.Audio;
import control.Controller;
import control.Input.KEYSTATE;
import control.Input;
import gameobject.Mask;

/**
 * Classe du joueur
 */
public class Player extends Entity {

	/* Movement */
	/**
	 * Liste contenant l'état des touches clavier
	 */
	private Input.KEYSTATE[] key; // W S A D SPACE MBLEFT MBRIGHT
	/**
	 * Vitesse en X et vitesse en Y
	 */
	private double vx, vy;

	/**
	 * Constante de vie maximal
	 */
	public static final int MAX_HEALTH = 100;

	/**
	 * S'il le joueur posssède l'Artefact
	 */
	private boolean haveArtefact;
	/**
	 * Si le joueur a gagné
	 */
	private boolean win;

	/**
	 * La liste des armes
	 */
	private Weapon[] weapons;

	/**
	 * L'arme que le joueur a en main
	 */
	private Weapon currentWeapon;

	/**
	 * Si le joueur est mort
	 */
	private boolean isDead;

	/**
	 * Constructeur du joueur
	 * 
	 * @param x
	 *            Origine en X
	 * @param y
	 *            Origine en Y
	 */
	public Player(double x, double y) {
		super(x, y, 100);
		sprite = "img/player.png";
		isDead = false;
		haveArtefact = false;
		win = false;

		/* Movement */
		vx = 0;
		vy = 0;

		key = new KEYSTATE[7];
		for (int i = 0; i < key.length; i++)
			key[i] = KEYSTATE.UP;

		mask = new Mask(0.25, 0.25, x, y);

		weapons = new Weapon[2];
		weapons[0] = new Sword(x, y, 10, 10);
		weapons[1] = new Bow(x, y, 5, 30, 0.3f);
		currentWeapon = weapons[0];
	}

	/**
	 * Change l'état d'une touche
	 * 
	 * @param index
	 *            Index de la touche
	 * @param state
	 *            Nouvelle état
	 */
	public void setKeyState(int index, KEYSTATE state) {
		key[index] = state;
	}

	@Override
	public void update(Floor floor) {
		if (!isDead && !win) {
			/* Movement */
			move(floor);
			mask.setPosition(x, y);
			if (key[4] == KEYSTATE.RELEASED)
				checkFloorChange();

			/* Combat */
			currentWeapon.setPosition(x, y);
			currentWeapon.setAngle(angle);
			currentWeapon.update();
			if (key[5] == KEYSTATE.PRESSED)
				currentWeapon.attack(floor);
			/* Change weapon */
			if (key[6] == KEYSTATE.PRESSED) {
				if (currentWeapon instanceof Bow) {
					currentWeapon = weapons[0];
					Controller
							.get()
							.getPane()
							.setCursor(
									new ImageCursor(Controller.get()
											.getRender()
											.getSprite("img/cursor.png")));
				} else {
					currentWeapon = weapons[1];
					Controller.get().getPane().setCursor(Cursor.NONE);
				}
			}

			if (health <= 0) {
				isDead = true;
				sprite = "img/playerdead.png";
				angle = 0;
				Audio.play("death");
			}
			if (health > 100)
				health = 100;
		} else {
			if (key[4] == KEYSTATE.RELEASED)
				Controller.get().initGame();
		}

		/* Input */
		updateInputState();
	}

	/**
	 * Déplace le joueur dans l'étage courant
	 * 
	 * @param floor
	 *            Étage courant
	 */
	private void move(Floor floor) {

		int xto = 0, yto = 0;
		double direction, speed;

		/* Define direction */
		if ((key[0] == KEYSTATE.PRESSED || key[0] == KEYSTATE.DOWN))
			yto--;
		if ((key[1] == KEYSTATE.PRESSED || key[1] == KEYSTATE.DOWN))
			yto++;

		if ((key[2] == KEYSTATE.PRESSED || key[2] == KEYSTATE.DOWN))
			xto--;
		if ((key[3] == KEYSTATE.PRESSED || key[3] == KEYSTATE.DOWN))
			xto++;

		/* Define speed */
		if (xto != 0 || yto != 0) {
			speed = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) + 0.05;
			if (speed > 0.125)
				speed = 0.125;

			direction = Math.atan2(yto, xto);
		} else {
			speed = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) - 0.05;
			if (speed < 0)
				speed = 0;
			direction = Math.atan2(vy, vx);
		}

		vx = Math.cos(direction) * speed;
		vy = Math.sin(direction) * speed;

		/* Check for collision */
		boolean hcheck = false;
		boolean vcheck = false;

		ArrayList<Mask> walls = floor.getWalls();
		for (Mask wall : walls) {
			mask.setX(x + vx);
			mask.setY(y + vy);
			if (Mask.collide(mask, wall)) {
				mask.setY(y);
				if (Mask.collide(mask, wall))
					hcheck = true;
				mask.setX(x);
				mask.setY(y + vy);
				if (Mask.collide(mask, wall))
					vcheck = true;
			}
		}

		/* Apply movement */
		if (hcheck == false)
			x += vx;
		if (vcheck == false)
			y += vy;
	}

	/**
	 * Vérifie le changement d'étage. (Et provoque une victoire s'il y a lieu)
	 */
	private void checkFloorChange() {
		double ex, ey;

		/* Going up */
		ex = Controller.get().getWorld().getFloor().getStartX();
		ey = Controller.get().getWorld().getFloor().getStartY();

		if (Math.sqrt(Math.pow(x - ex, 2) + Math.pow(y - ey, 2)) < 1) {
			if (Controller.get().getWorld().getFloor().getDepth() == 0
					&& this.haveArtefact) {
				win = true;
			} else {
				Audio.play("ladder");
				Controller.get().getWorld().changeFloor(-1);
			}

			return;
		}

		/* Going down */
		ex = Controller.get().getWorld().getFloor().getEndX();
		ey = Controller.get().getWorld().getFloor().getEndY();

		if (Math.sqrt(Math.pow(x - ex, 2) + Math.pow(y - ey, 2)) < 1) {
			Audio.play("ladder");
			Controller.get().getWorld().changeFloor(+1);
		}
	}

	/**
	 * Mets a jour le changement de touche.
	 */
	public void updateInputState() {
		/* Update input state */
		for (int i = 0; i < key.length; i++)
			if (key[i] == KEYSTATE.PRESSED)
				key[i] = KEYSTATE.DOWN;
			else if (key[i] == KEYSTATE.RELEASED)
				key[i] = KEYSTATE.UP;
	}

	/**
	 * Retourne l'arme courante
	 * 
	 * @return L'arme courante
	 */
	public Weapon getWeapon() {
		return currentWeapon;
	}

	/**
	 * Change l'angle du joueur.
	 */
	public void setAngle(double angle) {
		if (!isDead)
			this.angle = angle;
	}

	/**
	 * Retourne vrai si le joueur à l'Artefact.
	 * 
	 * @return Vrai s'il a l'artefact
	 */
	public boolean hasArtefact() {
		return haveArtefact;
	}

	/**
	 * Change si le joueur à l'Artefact
	 * 
	 * @param artefact
	 *            Vrai s'il vient d'obtenir l'artefacté
	 */
	public void setArtefact(boolean artefact) {
		this.haveArtefact = artefact;
	}

	/**
	 * Retourne si le joueur est mort
	 * 
	 * @return Vrai s'il est mort
	 */
	public boolean getDead() {
		return isDead;
	}

	public boolean getWin() {
		return win;
	}
}
