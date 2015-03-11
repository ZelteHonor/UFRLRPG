/**
 * WorldGen 1.0
 * Traduit du C++ par David Bourgault
 * Version originale par David Bourgault
 */

package world;

import world.World;
import java.util.ArrayList;

public class Generator {
	
	private final int ROOM_COUNT = 12;
	private final int ROOM_BIG_COUNT = 3;
	private final int ROOM_MAX_DISTANCE = World.SIZE/6;
	
	private final int CAVE_BLUR_RADIUS = 3;
	private final int CAVE_THRESHOLD = 117;
	
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
	
	private int exitdownx, exitdowny;
	private int exitupx, exitupy;
	
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
	
	/**
	 * Gï¿½nï¿½re le monde, sous forme d'un array de l'enum World.TILE
	 * @return world array
	 */
	public World.TILE[][] generate() {
		clear();
		generateRooms();
		generateTunnels();
		generatesExits();
		generateCaves();
		carve();
		connect();
		fillCaves();
		specify();		
		return tiles;
	}
	
	/**
	 * Prï¿½pare le monde, en settant les variables et vidant les arrays
	 */
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
	
	/**
	 * Gï¿½nï¿½re ROOM_COUNT salles, dont ROOOM_BIG_COUNT sont des "grosses" salles.
	 * S'assure que les salles ne se overlap pas.
	 */
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
			
			/* Vï¿½rifie pour le overlap */
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
	
	/**
	 * Sï¿½lectionne des salles, et les connectes avec des tunnels.
	 */
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
			
			/* Tente de trouver une salle qui est dans la limite acceptable, et qui est dï¿½jï¿½ connectï¿½e ï¿½ une autre salles */
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
			
			/* Tente de trouver une salle qui est dans la limite acceptable */
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
			
			/* Prend une salle au hasard */
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
	
	/**
	 * Gï¿½nï¿½re des grottes, basï¿½e sur un tableau de bruit
	 */
	private void generateCaves() {
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				caves[i][j] = (int)(Math.random()*255);
		
		/* Blur 2 fois pour meilleur resultat */
		blurCaves();
		blurCaves();
	}
	
	/**
	 * Génère l'entrée et la sortie du niveau
	 */
	private void generatesExits() {
		exitupx = (rooms.get(0).x + 1) + (int)(Math.random()*rooms.get(0).w);
		exitupy = (rooms.get(0).y + 1) + (int)(Math.random()*rooms.get(0).h);
		
		exitdownx = (rooms.get(rooms.size()-1).x + 1) + (int)(Math.random()*rooms.get(rooms.size()-1).w);
		exitdowny = (rooms.get(rooms.size()-1).y + 1) + (int)(Math.random()*rooms.get(rooms.size()-1).h);
	}
	
	/**
	 * Connecte les groupes de salles, jusqu'ï¿½ ce qu'elle soit toutes connectï¿½es
	 */
	private void connect() {
		int[][] map = new int[World.SIZE][World.SIZE];
		int map_count = 1;
		
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++)
				if (tiles[i][j] != World.TILE.WALL && tiles[i][j] != World.TILE.CAVE && map[i][j] == 0)
					fill(i, j, map, ++map_count);
		
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
			
			// NOT SUPPOSED TO HAPPEN
			if (!(from == null || target == null)) {
				System.out.println(from + " " + target);
				createTunnel(from, target);
				carve();
				connect();
			}
			
		}
					
	}
	
	/**
	 * Enlï¿½ve les grottes qui ne sont pas accesible des couloirs
	 */
	private void fillCaves() {
		int[][] map = new int[World.SIZE][World.SIZE];
		fill(exitupx, exitupy, map, 1);
		
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				if (map[i][j] == 0 && tiles[i][j] != World.TILE.WALL)
					tiles[i][j] = World.TILE.WALL;
	}
	
	/**
	 * Enlï¿½ve les tuiles qui ne sont pas visible, et spï¿½cifie le type de mur
	 */
	private void specify() {
		for(int i = 0; i < World.SIZE; i++)
			for(int j = 0; j < World.SIZE; j++)
				if (tiles[i][j] == World.TILE.WALL)
					if (nextTo(i,j,World.TILE.DONJON) || nextTo(i,j,World.TILE.TUNNEL));
					else if (nextTo(i,j,World.TILE.CAVE))
						tiles[i][j] = World.TILE.ROCK;
					else
						tiles[i][j] = World.TILE.BLACK;
	}
	
	/**
	 * Vï¿½rifie si une tuile type est ï¿½ proximitï¿½ directe de x,y
	 * @param x
	 * @param y
	 * @param type que l'on cherche
	 * @return boolean
	 */
	private boolean nextTo(int x, int y, World.TILE type) {
		for(int i = -1; i <= 1; i++)
			for(int j = -1; j <= 1; j++)
				if ((x+i >= 0 && y+j >= 0 && x+i < World.SIZE && y+j < World.SIZE))
					if (tiles[x+i][y+j] == type)
						return true;
		return false;
	}
	
	/**
	 * Fonction rï¿½cursive pour inonder les salles
	 * @param x de dï¿½part
	 * @param y de dï¿½part
	 * @param map le tableau de stockage
	 * @param val la valeur ï¿½ ï¿½crire
	 */
	private void fill(int x, int y,int[][] map, int val) {
		
		if (x >= World.SIZE-1 || y >= World.SIZE-1)
			return;
		
		map[x][y] = val;
		
		if (tiles[x-1][y] != World.TILE.WALL && map[x-1][y] == 0)
			fill(x-1, y, map, val);
		if (tiles[x+1][y] != World.TILE.WALL && map[x+1][y] == 0)
			fill(x+1, y, map, val);
		if (tiles[x][y-1] != World.TILE.WALL && map[x][y-1] == 0)
			fill(x, y-1, map, val);
		if (tiles[x][y+1] != World.TILE.WALL && map[x][y+1] == 0)
			fill(x, y+1, map, val);
	}
	
	/**
	 * Crï¿½e un tunnel entre deux salles
	 * @param from
	 * @param target
	 */
	private void createTunnel(Room from, Room target) {
		Room a = new Room(0,0,0,0);
		Room b = new Room(0,0,0,0);
		boolean large = (distance(from,target) < ROOM_MAX_DISTANCE);
		
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
	
	/**
	 * Fonction de flou sur l'array des grottes pour attenuer le bruit alï¿½atoire
	 */
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
	
	/**
	 * Applique les salles logiques, les tunnels logiques et les grottes dans le tableau final de monde 
	 */
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
		
		tiles[exitupx][exitupy] = World.TILE.EXITUP;
		tiles[exitdownx][exitdowny] = World.TILE.EXITDOWN;
	}
	
	/**
	 * Calcule la distance entre deux salles a,b
	 * @param a
	 * @param b
	 * @return distance between a and b
	 */
	private int distance(Room a, Room b) {
		return (int) Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
	}
	
	public int getStartX() {
		return exitupx * 64;
	}
	public int getStartY() {
		return exitupy * 64;
	}
}
