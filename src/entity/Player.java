package entity;

import java.util.ArrayList;

import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import weapons.Bow;
import weapons.Sword;
import weapons.Weapon;
import world.Floor;
import world.World.TILE;
import control.Audio;
import control.Controller;
import control.Input.KEYSTATE;
import control.Input;
import gameobject.Item;
import gameobject.Mask;

public class Player extends Entity {

	/* Movement */
	private Input.KEYSTATE[] key; // W S A D SPACE MBLEFT MBRIGHT
	private double vx, vy;
	
	public static final int MAX_HEALTH = 100;

	private Weapon weapon;
	
	private boolean dead;
	

	public Player(double x, double y) {
		super(x, y, 100);
		sprite = "img/player.png";
		dead = false;

		/* Movement */
		vx = 0;
		vy = 0;

		key = new KEYSTATE[7];
		for (int i = 0; i < key.length; i++)
			key[i] = KEYSTATE.UP;
		
		mask = new Mask(0.25, 0.25,x,y);
		weapon = new Sword(x, y, 10, 10);
	}

	public void setKeyState(int index, KEYSTATE state) {
		key[index] = state;
	}

	@Override
	public void update(Floor floor) {
		if (!dead) {
			/* Movement */
			move(floor);
			mask.setPosition(x,y);
			if (key[4] == KEYSTATE.RELEASED)
				checkFloorChange();
			
			/* Combat */
			weapon.setPosition(x, y);
			weapon.setAngle(angle);
			weapon.update();
			if (key[5] == KEYSTATE.PRESSED)
				weapon.attack(floor);
			/* Change weapon*/
			if (key[6] == KEYSTATE.PRESSED) {
				if(weapon instanceof Bow) { 
					weapon = new Sword(x, y, 10, 10);
					Controller.get().getPane().setCursor(new ImageCursor(Controller.get().getRender().getSprite("img/cursor.png")));
				} else {
					weapon = new Bow(x, y, 5, 5, 0.3f);
					Controller.get().getPane().setCursor(Cursor.NONE);
				}
			}
			
			if (health <= 0) {
				dead = true;
				sprite = "img/playerdead.png";
				angle = 0;
				Audio.play("death");
			}
		}
		else {
			health = 0;
			if (key[4] == KEYSTATE.RELEASED)
				Controller.get().initGame();
		}
		
		/* Input */
		updateInputState();
	}

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
		boolean hcheck =  false;
		boolean vcheck = false;
		
		ArrayList<Mask> walls = floor.getWalls();
		for(Mask wall : walls) {
			mask.setX(x+vx);
			mask.setY(y+vy);
			if (Mask.collide(mask, wall)) {
				mask.setY(y);
				if (Mask.collide(mask,wall))
					hcheck = true;
				mask.setX(x);
				mask.setY(y+vy);
				if (Mask.collide(mask,wall))
					vcheck = true;		
			}
		}
		
		/* Apply movement */
		if (hcheck == false)
			x += vx;
		if (vcheck == false)
			y += vy;
	}
	
	private void checkFloorChange() {
		double ex, ey;
		
		/* Going up */
		ex = Controller.get().getWorld().getFloor().getStartX();
		ey = Controller.get().getWorld().getFloor().getStartY();
		
		if (Math.sqrt(Math.pow(x-ex,2)+Math.pow(y-ey,2)) < 1) {
			Controller.get().getWorld().changeFloor(-1);
			Audio.play("ladder");
			return;
		}
		
		/* Going down */
		ex = Controller.get().getWorld().getFloor().getEndX();
		ey = Controller.get().getWorld().getFloor().getEndY();
		
		
		if (Math.sqrt(Math.pow(x-ex,2)+Math.pow(y-ey,2)) < 1)
			Controller.get().getWorld().changeFloor(+1);
			Audio.play("ladder");
	}
	
	public void updateInputState() {
		/* Update input state */
		for (int i = 0; i < key.length; i++)
			if (key[i] == KEYSTATE.PRESSED)
				key[i] = KEYSTATE.DOWN;
			else if (key[i] == KEYSTATE.RELEASED)
				key[i] = KEYSTATE.UP;
	}
	
	public Weapon getWeapon() {
		return weapon;
	}
	
	public void setAngle(double angle) {
		if (!dead)
			this.angle = angle;
	}
}
