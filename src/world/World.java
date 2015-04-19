package world;

import java.util.ArrayList;

public class World {
	public static enum TILE {
		BLACK, WALL, ROCK, CAVE, DONJON, TUNNEL, EXITUP, EXITDOWN
	};

	public static final int SIZE = 64;
	public static final int LEVEL_COUNT = 1;

	private ArrayList<Floor> floors;
	private Floor floor;

	public World() {
		floors = new ArrayList<Floor>();
		for (int i = 0; i < LEVEL_COUNT; i++) {
			floors.add(new Floor(i));
		}

		floor = floors.get(0);
	}

	public Floor getFloor(int depth) {
		return floors.get(depth);
	}

	public Floor getFloor() {
		return floor;
	}
	
	public boolean reachedExit()
	{
		double x = floor.getPlayer().getX();
		double y = floor.getPlayer().getY();
		if(floor.getTiles()[(int) x][(int)y].equals(TILE.EXITDOWN))
		{
			return true;
			
		}else
		{
			return false;
		}
	}

}
