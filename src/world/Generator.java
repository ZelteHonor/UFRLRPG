/**
 * WorldGen 1.0
 * Traduit du C++ par David Bourgault
 * Version original par David Bourgault
 */

package world;

import java.util.ArrayList;

public class Generator {
	
	private final int ROOM_COUNT = 48;
	private final int ROOM_BIG_COUNT = 12;
	private final int ROOM_MAX_DISTANCE = World.SIZE/6;
	
	private final int CAVE_BLUR_RADIUS = 3;
	private final int CAVE_THRESHOLD = 118;
	
	/* Rooms */
	private class Room {
		public int x, y, w,h;
		
		public Room(int x, int y, int w, int h) {
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
		}
	}
	
	private ArrayList<Room> rooms;
	private ArrayList<Room> tunnels;
	
	/* World */
	private World.TILE[][] tiles;
	private char[][] caves; 
	
	/* Public */
	public Generator() {
		tiles = new World.TILE[World.SIZE][World.SIZE];
		caves = new char[World.SIZE][World.SIZE];
		
		rooms = new ArrayList<Room>();
		tunnels = new ArrayList<Room>();
	}
	
	public World.TILE[][] generate() {
		return generate(true);
	}
	
	public World.TILE[][] generate(boolean caves) {
		clear();
		generateRooms();
		generateTunnels();
		return tiles;
	}
	
	/* Generation */
	private void clear() {
		for(int i = 0; i < World.SIZE; i++) {
			for(int j = 0; j < World.SIZE; j++) {
				tiles[i][j] = World.TILE.WALL;
				caves[i][j] = 0;	
			}
		}
		
		rooms.clear();
		tunnels.clear();
	}
	
	private void generateRooms() {
		int bigroom_count = 0;
		
		while (rooms.size() < ROOM_COUNT) {
			boolean big = (bigroom_count < ROOM_BIG_COUNT);
			boolean pass = true;
			
			Room room;
			if (big)
				room = new Room(0, 0, (int)(8+Math.random()*6), (int)(8+Math.random()*6));
			else
				room = new Room(0, 0, (int)(4+Math.random()*5), (int)(4+Math.random()*5));
			
			room.x = (int)(Math.random() * (World.SIZE - room.w));
			room.y = (int)(Math.random() * (World.SIZE - room.w));
			
			for (int i = 0; i < rooms.size(); i++) {
				Room other = rooms.get(i);
				if (!((other.x > room.x + room.w) || (other.x + other.w > room.x) || (other.y > room.y + room.h) || (other.y + other.h > room.y)))
					pass = false;
			}
			
			if (pass) {
				rooms.add(room);
				if (big)
					bigroom_count++;
			}
		}
	}
	
	private void generateTunnels() {
		ArrayList<Room> network = new ArrayList<Room>();
		network.add(rooms.get(0));
		rooms.remove(0);
		
		while (rooms.size() > 0) {
			boolean success = false;
			Room target = rooms.get((int)(Math.random()*rooms.size()));
			
			ArrayList<Room> available = (ArrayList<Room>) network.clone();
			ArrayList<Room> tested = new ArrayList<Room>();
			
			while (!success && available.size() > 0) {
				Room from = available.get((int)(Math.random()*available.size()));
			}
		}
	}
	
	private int distance(Room a, Room b) {
		return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
}
