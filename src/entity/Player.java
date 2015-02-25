package entity;

import java.util.ArrayList;

import control.Controller;
import control.Controller.KEYSTATE;
import gameObjects.Items;

public class Player extends Entity{
	
	private Controller.KEYSTATE[] key;

	public Player(int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception,ArrayList<Items> inventory) {
		super(level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		key = new Controller.KEYSTATE[4];
		for(int i = 0; i < key.length ; i++)
		{
			key[i] = Controller.KEYSTATE.UP;
		}
	}
	
	public void setKeyState(int index, Controller.KEYSTATE state)
	{
		key[index] = state;
	}

	@Override
	public void update() {
		
		
		for(int i = 0; i < key.length ; i++)
			if(key[i] == KEYSTATE.PRESSED)
				key[i] = KEYSTATE.DOWN;
			else if(key[i] == KEYSTATE.RELEASED)
				key[i] = KEYSTATE.UP;
		
	}

}
