/**
 * WorldGen 1.0
 * Traduit du C++ par David Bourgault
 * Version original par David Bourgault
 */

package world;

import java.util.ArrayList;

public class Generator {
	
	private final int ROOM_COUNT = 24;
	private final int ROOM_BIG_COUNT = 6;
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
	private int[][] caves; 
	
	/* Public */
	public Generator() {
		tiles = new World.TILE[World.SIZE][World.SIZE];
		caves = new int[World.SIZE][World.SIZE];
		
		rooms = new ArrayList<Room>();
		tunnels = new ArrayList<Room>();
	}
	
	public World.TILE[][] generate() {
		clear();
		generateRooms();
		generateTunnels();
		generateCaves();
		carve();
		connect();
		fillCaves();
		
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
			room.y = (int)(Math.random() * (World.SIZE - room.h));
			
			for (int i = 0; i < rooms.size(); i++) {
				Room other = rooms.get(i);
				if (!((other.x > room.x + room.w) || (other.x + other.w < room.x) || (other.y > room.y + room.h) || (other.y + other.h < room.y)))
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
			Room from;
			
			ArrayList<Room> available = (ArrayList<Room>) network.clone();
			ArrayList<Room> tested = new ArrayList<Room>();
			
			while (!success && available.size() > 0) {
				from = available.get((int)(Math.random()*available.size()));
				if (distance(from, target) <= ROOM_MAX_DISTANCE) {
					createTunnel(from, target);
					success = true;
				}
				else {
					tested.add(from);
					available.remove(from);
				}
			}
			
			tested.clear();
			available = (ArrayList<Room>) rooms.clone();
			
			while (!success && available.size() > 0) {
				from = available.get((int)(Math.random()*available.size()));
				if (distance(from, target) <= ROOM_MAX_DISTANCE) {
					createTunnel(from, target);
					success = true;
				}
				else {
					tested.add(from);
					available.remove(from);
				}
			}
			
			if (success == false) {
				available = (ArrayList<Room>) network.clone();
				from = available.get((int)(Math.random()*available.size()));
				createTunnel(from, target);
			}
		
			network.add(target);
			rooms.remove(target);
		}
		
		rooms = (ArrayList<Room>) network.clone();
	}
	
	private void generateCaves() {
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				caves[i][j] = (int)(Math.random()*255);
		
		blurCaves();
		blurCaves();
	}
	
	private void connect() {
		int[][] map = new int[World.SIZE][World.SIZE];
		int map_count = 1;
		
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++)
				if (tiles[i][j] != World.TILE.WALL && tiles[i][j] != World.TILE.CAVE && map[i][j] == 0)
					fill(i, j, map, map_count++);
		map_count--;
	
		if (map_count > 1) {
			ArrayList<Room> in = new ArrayList<Room>();
			ArrayList<Room> out = new ArrayList<Room>();
			int best = World.SIZE * 2;
			Room from = null;
			Room target = null;
			
			for(int k = 0; k < rooms.size(); k++) {
				if (map[rooms.get(k).x+1][rooms.get(k).y+1] == map_count)
					in.add(rooms.get(k));
				else
					out.add(rooms.get(k));
			}
			
			for(int i = 0; i < in.size(); i++) {
				for(int j = 0; j < out.size(); j++) {
					if (distance(in.get(i), out.get(j)) < best) {
						best = distance(in.get(i), out.get(j));
						from = in.get(i);
						target = out.get(j);
					}
				}
			}
			
			createTunnel(from, target);
			carve();
			connect();
		}
					
	}
	
	private void fillCaves() {
		int[][] map = new int[World.SIZE][World.SIZE];
		fill(rooms.get(0).x+1, rooms.get(0).y+1, map, 1);
		
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				if (map[i][j] == 0 && tiles[i][j] != World.TILE.WALL)
					tiles[i][j] = World.TILE.WALL;
	}
	
	private void fill(int x, int y,int[][] map, int val) {
		map[x][y] = val;
		
		if (tiles[x-1][y] != World.TILE.WALL && map[x-1][y] == 0 && x > 0)
			fill(x-1, y, map, val);
		if (tiles[x+1][y] != World.TILE.WALL && map[x+1][y] == 0 && x < World.SIZE-1)
			fill(x+1, y, map, val);
		if (tiles[x][y-1] != World.TILE.WALL && map[x][y-1] == 0 && y > 0)
			fill(x, y-1, map, val);
		if (tiles[x][y+1] != World.TILE.WALL && map[x][y+1] == 0 && y < World.SIZE-1)
			fill(x, y+1, map, val);
	}
	
	private void createTunnel(Room from, Room target) {
		Room a = new Room(0,0,0,0);
		Room b = new Room(0,0,0,0);
		boolean large = (distance(from,target) > ROOM_MAX_DISTANCE);
		
		int nx, ny;		
		nx = (int)(target.x + Math.random() * target.w);
		ny = (int)(from.y + Math.random() * from.h);
		
		if (nx > from.x) {
			a.x = from.x;
			a.w = nx - from.x + 1;
			if (large)
				a.w++;
		}
		else {
			a.x = nx;
			a.w = from.x - nx + 1;
		}
		a.y = ny;
		a.h = 1;
		if (large)
			a.h++;

		if (ny > target.y) {
			b.y = target.y;
			b.h = ny - target.y + 1;
			if (large)
				b.h++;
		}
		else {
			b.y = ny;
			b.h = target.y - ny + 1;
		}
		b.x = nx;
		b.w = 1;
		if (large)
			b.w++;
		
		tunnels.add(a);
		tunnels.add(b);
	}
	
	private void blurCaves() {
		int[][] blur_map = new int[World.SIZE][World.SIZE];
		
		for(int i = 0; i < World.SIZE; i++) {
			for(int j = 0; j < World.SIZE; j++) {
				int total = 0;		
				int count = 0;
				for (int a = -CAVE_BLUR_RADIUS; a <= CAVE_BLUR_RADIUS; a++){
					for (int b = -CAVE_BLUR_RADIUS; b <= CAVE_BLUR_RADIUS; b++) {
						if (i+a >= 0 && j+b >= 0 && i+a < World.SIZE && j+b < World.SIZE) {
							total += caves[i+a][j+b];
							count++;
						}
					}
				}
				blur_map[i][j] = (int)(total /count);
			}
		}
		
		caves = blur_map;
	}
	
	private void carve() {
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				if (caves[i][j] <= CAVE_THRESHOLD)
					tiles[i][j] = World.TILE.CAVE;

		for (int k = 0; k < tunnels.size(); k++) {
			Room room = tunnels.get(k);
			for(int i = room.x; i < room.x + room.w; i++) {
				for(int j = room.y; j < room.y + room.h; j++) {
					tiles[i][j] = World.TILE.TUNNEL;
				}
			}
		}
		
		for (int k = 0; k < rooms.size(); k++) {
			Room room = rooms.get(k);
			for(int i = room.x; i < room.x + room.w; i++) {
				for(int j = room.y; j < room.y + room.h; j++) {
					tiles[i][j] = World.TILE.DONJON;
				}
			}
		}
		
	
		
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				if (i == 0 || j == 0 || i == World.SIZE -1 || j == World.SIZE -1)
					tiles[i][j] = World.TILE.WALL;
	}
	
	private int distance(Room a, Room b) {
		return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public static void main(String args[]) {
		Generator gen = new Generator();
		World.TILE[][] data = gen.generate();
		
		for(int i = 0; i < World.SIZE; i++) {
			for(int j = 0; j < World.SIZE; j++) {
				switch (data[i][j].ordinal()) {
				case 0: System.out.print(" "); break;
				case 1: System.out.print("*"); break;
				case 2: System.out.print("#"); break;
				case 3: System.out.print("%"); break;
				}
			}
			System.out.println();
		}
	}
}
