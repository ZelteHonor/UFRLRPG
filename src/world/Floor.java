package world;

import entity.Player;
import gameobject.GameObject;
import gameobject.Mask;
import java.util.ArrayList;
import world.World.TILE;

public class Floor {
		
	/**
	 *liste statique des tuiles qui forme le floor
	 */
	private World.TILE[][] tiles;
	/**
	 * les GameObjects contenu dans le floor
	 */
	private ArrayList<GameObject> objects;
	/**
	 * 
	 */
	private ArrayList<Mask> walls;
	/**
	 * le player pr�sent dans le floor
	 */
	private Player player;
	/**
	 * les coordonn�es du point d'entr�e du floor
	 */
	private int startx, starty;
	/**
	 * le point de sortie du floor
	 */
	private int endx, endy;

	/**
	 * la profondeur du floor relatif aux autres floors
	 */
	private int depth;

	public Floor(int depth) {
		objects = new ArrayList<GameObject>();
		if (depth == -1) {
			tiles = new World.TILE[World.SIZE][World.SIZE];
			for (int i = 0; i < World.SIZE; i++)
				for (int j = 0; j < World.SIZE; j++)
					if (i == 0 || j == 0 || i == World.SIZE-1 || j == World.SIZE-1)
						tiles[i][j] = TILE.WALL;
					else
						tiles[i][j] = TILE.DONJON;
					startx = 10;
					starty = 10;						
		} else {
			Generator gen = new Generator();
			tiles = gen.generate();
			startx = gen.getStartX();
			starty = gen.getStartY();
			
			endx = gen.getEndX();
			endy = gen.getEndY();
			
			this.depth = depth;
			
			MonsterGenerator mg = new MonsterGenerator();
			mg.generateMonster(this);
			
		}
		
		
		walls = new ArrayList<Mask>(0);
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++)
				if (tiles[i][j] == TILE.WALL || tiles[i][j] == TILE.ROCK)
					walls.add(new Mask(1,1,i + 0.5,j + 0.5));				
	}

	

	public ArrayList<Mask> getWalls() {
		return walls;
	}
	
	/**
	 * 
	 * @return les GameObjects pr�sent sur le floor
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<GameObject> objects) {
		this.objects = objects;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}

	public World.TILE[][] getTiles() {
		return tiles;
	}
	
	public double getStartX() {
		return startx + 0.5;
	}
	
	public double getStartY() {
		return starty + 0.5;
	}
	
	public double getEndX() {
		return endx + 0.5;
	}
	
	public double getEndY() {
		return endy + 0.5;
	}

	public int getDepth() {
		return depth;
	}
}
