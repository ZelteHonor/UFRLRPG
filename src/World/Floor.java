package World;

public class Floor {
	// J'utilise un int[][] au lieu d'un byte[][] parce que le java est incompétent.
	private int[][] tiles = new int[World.FLOOR_SIZE][World.FLOOR_SIZE];
	
	// La profondeur du "floor" et donc le niveau de difficulté
	private int depth;
	
	public Floor(int depth)	{
		this.depth = depth;
		clear_to(1);
	}
	
	private void clear_to(int value) {
		for (int i = 0; i < World.FLOOR_SIZE; i++) {
			for (int j = 0; j < World.FLOOR_SIZE; j++) {
				tiles[i][j] = value;
			}
		}
	}
}
