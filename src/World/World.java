package World;

import java.util.ArrayList;

public class World {
	public static final int FLOOR_SIZE = 256;
	public static final int FLOOR_COUNT = 16;
	
	private ArrayList<Floor> floors;
	
	public World() {
		floors.add(new Floor());
	}
}
