package entity;

import java.util.ArrayList;

import world.Floor;
import world.World.TILE;
import control.Controller;
import control.Controller.KEYSTATE;
import gameObjects.Items;
import gameObjects.Mask;
import gameObjects.Ranges;
import gameObjects.Weapons;

public class Player extends Entity {

	/* Movement */
	private Controller.KEYSTATE[] key; // W S A D SPACE MBLEFT MBRIGHT
	private double vx, vy;

	private Weapons currentWeapon;
	

	public Player(double x, double y) {
		super(x, y, 100, null);

		/* Movement */
		vx = 0;
		vy = 0;

		key = new Controller.KEYSTATE[6];
		for (int i = 0; i < key.length; i++) {
			key[i] = Controller.KEYSTATE.UP;
		}
		
		mask = new Mask(0.25, 0.25,x,y);
		currentWeapon = new Ranges(x, y, 10, false, 10, 5, 2, 0.2f);
	}

	public void setKeyState(int index, Controller.KEYSTATE state) {
		key[index] = state;
	}

	@Override
	public void update(Floor floor) {
		move(floor);
		
		if (key[4] == KEYSTATE.RELEASED)
			checkFloorChange();
		
		currentWeapon.setX(this.x);
		currentWeapon.setY(this.y);
		currentWeapon.setAngle(this.angle);
		
		if (key[5] == KEYSTATE.PRESSED)
			currentWeapon.attack(floor);
		
		/* Update input state */
		for (int i = 0; i < key.length; i++)
			if (key[i] == KEYSTATE.PRESSED)
				key[i] = KEYSTATE.DOWN;
			else if (key[i] == KEYSTATE.RELEASED)
				key[i] = KEYSTATE.UP;
	}

	private void move(Floor floor) {

		int xto = 0, yto = 0;
		double direction, speed;

		if ((key[0] == KEYSTATE.PRESSED || key[0] == KEYSTATE.DOWN))
			yto--;
		if ((key[1] == KEYSTATE.PRESSED || key[1] == KEYSTATE.DOWN))
			yto++;

		if ((key[2] == KEYSTATE.PRESSED || key[2] == KEYSTATE.DOWN))
			xto--;
		if ((key[3] == KEYSTATE.PRESSED || key[3] == KEYSTATE.DOWN))
			xto++;

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
		if (hcheck == false)
			x += vx;
		if (vcheck == false)
			y += vy;
	}
	
	private void checkFloorChange() {
		double ex, ey;
		ex = Controller.get().getWorld().getFloor().getStartX();
		ey = Controller.get().getWorld().getFloor().getStartY();
		
		if (Math.sqrt(Math.pow(x-ex,2)+Math.pow(y-ey,2)) < 1) {
			Controller.get().getWorld().changeFloor(-1);
			return;
		}
		
		ex = Controller.get().getWorld().getFloor().getEndX();
		ey = Controller.get().getWorld().getFloor().getEndY();
		
		if (Math.sqrt(Math.pow(x-ex,2)+Math.pow(y-ey,2)) < 1)
			Controller.get().getWorld().changeFloor(+1);
	}
}
