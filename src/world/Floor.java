package world;

import gameObjects.GameObjects;
import gameObjects.Mask;

import java.awt.Point;
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
	
	private class Wall //======================================Reste à l'intégrer!
	{
		private World.TILE[][] world;
		private ArrayList<Point[]> mur;
		private ArrayList<Point[]> murH;
		private ArrayList<Point[]> murV;
		
		public Wall(World.TILE[][] world) {
			
			this.world = world;
			mur = new ArrayList<Point[]>();
			murH = new ArrayList<Point[]>();
			murV = new ArrayList<Point[]>();
			
		}
		
		/**
		 * Recalcule les murs à l'écran à partir d'un centre
		 * 
		 * @param x
		 * @param y
		 */
		public void recalculate()
		{
			
			System.out.println("WORLD " + world.length +" "+ world[0].length);
			
			
			//Parcoure les cases autour d'un point (x,y)
			for(int j = 0;j < world.length;j++)//===================================================================
			{
				for(int i = 0;i < world[j].length;i++)
				{

					if(isWall(i, j))
					{

						try{

							if(isWall(i+1, j))//Regarde à droite
							{
							
								murH.add(new Point[]{new Point(i,j),rightBorder(i+1,j)});
							
							}
							
						}catch(IndexOutOfBoundsException e){}
								
						try{
							
							if(isWall(i, j+1))//Regade en dessous (au dessus dans la structure, mais en dessous en mode graphique)
							{

								murV.add(new Point[]{new Point(i,j),underBorder(i,j+1)});
							
							}
							
						}catch(IndexOutOfBoundsException e){}

						
						
					}
				
				}
			}
			
			deleteRepetitions();
			
			
				System.out.println("APRES============================================================================================");
				for(int i = 0;i < mur.size(); i++)
				{
					System.out.println("("+mur.get(i)[0].x+","+mur.get(i)[0].y+")-("+mur.get(i)[1].x+","+mur.get(i)[1].y+")");
				}
				
				System.out.println("APRES H==========================================================================================");
				for(int i = 0;i < murH.size(); i++)
				{
					System.out.println("("+murH.get(i)[0].x+","+murH.get(i)[0].y+")-("+murH.get(i)[1].x+","+murH.get(i)[1].y+")");
				}
				
				System.out.println("APRES V==========================================================================================");
				for(int i = 0;i < murV.size(); i++)
				{
					System.out.println("("+murV.get(i)[0].x+","+murV.get(i)[0].y+")-("+murV.get(i)[1].x+","+murV.get(i)[1].y+")");
				}

		}
		
		/**
		 * 
		 * @param x
		 * @param y
		 * @return si c'est un mur
		 */
		private boolean isWall(int x, int y)
		{
			return (world[x][y] == TILE.WALL || world[x][y] == TILE.ROCK);
		}
		
		/**
		 * 
		 * @param x
		 * @param y
		 * @return Le point le plus à droite.
		 */
		private Point rightBorder(int x, int y)
		{
			Point border = new Point(x,y);
			
			try
			{
			
				if(world[x+1][y] == TILE.WALL || world[x+1][y] == TILE.ROCK)
				{
					border = rightBorder(x+1,y);
				}
			
			}catch(IndexOutOfBoundsException e){}
			
			return border;
	
		}
		
		/**
		 * 
		 * @param x
		 * @param y
		 * @return Le point le plus bas.
		 */
		private Point underBorder(int x, int y)
		{
			Point border = new Point(x,y);
			
			try
			{
			
				if(world[x][y+1] == TILE.WALL || world[x][y+1] == TILE.ROCK)
				{
					border = underBorder(x,y+1);
				}
			
			}catch(IndexOutOfBoundsException e){}
			
			return border;
	
		}
		
		/**
		 * Suppression des murs dans les murs.
		 */
		private void deleteRepetitions()
		{
			//Horizontal
			for(int i = 0; i < murH.size(); i++)
			{
				for(int j = 0; j < murH.size(); j++)
				{
					try
					{
						if(murH.get(i) != murH.get(j) && murH.get(i)[1].distance(murH.get(j)[1]) == 0)
						{
							murH.remove(j);
							j--;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
			
			//Vertical
			for(int i = 0; i < murV.size(); i++)
			{
				for(int j = 0; j < murV.size(); j++)
				{
					try
					{
						if(murV.get(i) != murV.get(j) && murV.get(i)[1].distance(murV.get(j)[1]) == 0)
						{
							murV.remove(j);
							j--;
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
			
		}
	}
}
