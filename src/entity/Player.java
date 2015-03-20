package entity;

import java.util.ArrayList;

import world.Floor;
import world.World.TILE;
import control.Controller;
import control.Controller.KEYSTATE;
import gameObjects.Items;

public class Player extends Entity {

	/* Movement */
	private Controller.KEYSTATE[] key; // WSAD
	private double vx, vy;

	public Player(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception,
			ArrayList<Items> inventory) {
		super(level, health, intellect, strenght, agility, mana, speed,
				perception, inventory);

		/* Movement */
		vx = 0;
		vy = 0;

		key = new Controller.KEYSTATE[6];
		for (int i = 0; i < key.length; i++) {
			key[i] = Controller.KEYSTATE.UP;
		}
	}

	public void setKeyState(int index, Controller.KEYSTATE state) {
		key[index] = state;
	}

	@Override
	public void update(Floor floor) {
		move(floor);

		/* Angle */

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

		if ((key[0] == KEYSTATE.PRESSED || key[0] == KEYSTATE.DOWN) && !isWallCollisionning(floor, 0, -1))
			yto--;
		if ((key[1] == KEYSTATE.PRESSED || key[1] == KEYSTATE.DOWN) && !isWallCollisionning(floor, 0, 1))
			yto++;

		if ((key[2] == KEYSTATE.PRESSED || key[2] == KEYSTATE.DOWN) && !isWallCollisionning(floor, -1, 0))
			xto--;
		if ((key[3] == KEYSTATE.PRESSED || key[3] == KEYSTATE.DOWN) && !isWallCollisionning(floor, 1, 0))
			xto++;

		if (xto != 0 || yto != 0) {
			speed = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) + 1;
			if (speed > 5)
				speed = 5;

			direction = Math.atan2(yto, xto);
		} else {
			speed = Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2)) - 1;
			if (speed < 0)
				speed = 0;
			direction = Math.atan2(vy, vx);
		}

		vx = Math.cos(direction) * speed;
		vy = Math.sin(direction) * speed;

		x += vx;
		y += vy;
		System.out.println("POSITION DU JOUEUR " + x / 64 + " " + y / 64);
	}

	private boolean isWallCollisionning(Floor floor, int cx, int cy) {
		boolean ans = false;
		int px = x/64;
		int py = y/64;
		
		if (floor.getTiles()[px + cx][py + cy].equals(TILE.WALL) || floor.getTiles()[px + cx][py + cy].equals(TILE.ROCK)) {
			ans = true;
			System.out.println("JE SUIS PRIT DANS UN MUR");
		}
		return ans;

	}
}
