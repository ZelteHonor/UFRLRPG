package world;

import gameObjects.GameObjects;

import java.util.ArrayList;

public class Floor {
	private World.TILE[][] tiles;
	private ArrayList<GameObjects> objects;
	private int startx, starty;
	
	private int depth;
	
	public Floor(int depth) {
		Generator gen = new Generator();
		tiles = gen.generate();
		startx = gen.getStartX();
		starty = gen.getStartY();
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
