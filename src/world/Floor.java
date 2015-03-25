package world;

import gameObjects.GameObjects;
import gameObjects.Mask;

import java.util.ArrayList;

import world.World.TILE;
public class Floor {
		
	private World.TILE[][] tiles;
	private ArrayList<GameObjects> objects;
	private ArrayList<Mask> walls;
	private int startx, starty;

	private int depth;

	public Floor(int depth) {
		if (depth == -1) {
			tiles = new World.TILE[World.SIZE][World.SIZE];
			for (int i = 0; i < World.SIZE; i++)
				for (int j = 0; j < World.SIZE; j++)
					if (i == 0 || j == 0 || i == World.SIZE-1 || j == World.SIZE-1)
						tiles[i][j] = TILE.WALL;
					else
						tiles[i][j] = TILE.DONJON;
						
		} else {
			Generator gen = new Generator();
			tiles = gen.generate();
			startx = gen.getStartX();
			starty = gen.getStartY();
		}
		
		walls = new ArrayList<Mask>(0);
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++)
				if (tiles[i][j] == TILE.WALL || tiles[i][j] == TILE.ROCK)
					walls.add(new Mask(i*64 + 32,j*64 + 32,64,64));
					
	}

	public ArrayList<Mask> getWalls() {
		return walls;
	}
	
	public ArrayList<GameObjects> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObjects> objects) {
		this.objects = objects;
	}

	public World.TILE[][] getTiles() {
		return tiles;
	}
}
