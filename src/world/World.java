package world;

import java.util.ArrayList;

import control.Controller;

public class World {
	public static enum TILE {
		BLACK, WALL, ROCK, CAVE, DONJON, TUNNEL, EXITUP, EXITDOWN
	};

	public static final int SIZE = 64;
	public static final int LEVEL_COUNT = 10;

	private ArrayList<Floor> floors;
	private int current;

	public World() {
		floors = new ArrayList<Floor>();
		
		for (int i = 0; i < LEVEL_COUNT; i++)
			floors.add(new Floor(i));

		current = 0;
	}

	public Floor getFloor(int depth) {
		return floors.get(depth);
	}

	public Floor getFloor() {
		return floors.get(current);
	}
	
	public void changeFloor(int direction) {
		if (direction + current < 0 || direction + current >= LEVEL_COUNT)
			return;
		
		current += direction;
		
		Controller.get().setObjects(floors.get(current).getObjects());
		floors.get(current).setPlayer(Controller.get().getPlayer());
		Controller.get().getRender().setWorld(floors.get(current).getTiles());
		
		if (direction == -1) {
			Controller.get().getPlayer().setX(floors.get(current).getEndX());
			Controller.get().getPlayer().setY(floors.get(current).getEndY());
		}
		else {
			Controller.get().getPlayer().setX(floors.get(current).getStartX());
			Controller.get().getPlayer().setY(floors.get(current).getStartY());
		}
	}
}
