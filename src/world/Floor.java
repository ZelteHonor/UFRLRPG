package world;

public class Floor {
	private World.TILE[][] tiles;
	private int depth;
	
	public Floor(int depth) {
		tiles = new World.TILE[World.SIZE][World.SIZE];
	}
}
