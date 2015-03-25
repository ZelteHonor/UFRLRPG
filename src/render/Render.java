/**
 * 
 */
package render;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import entity.Player;
import sun.tools.jar.Main;
import gameObjects.GameObjects;
import world.Generator;
import world.World;
import world.World.TILE;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

/**
 * Cette classe dessine sur un Canvas le monde et ces objets.
 * 
 * @author Anthony Boudreau
 * @version 0
 *
 */
public class Render {
	
	//Interface Graphique
	private Canvas GUI;
	
	//Dimension et r�solution d'affichage
	private final int DH;
	private final int DW;
	private final int RESOLUTION;
	
	private double cx = 0;
	private double cy = 0;

	//� afficher
	private World.TILE[][] world;
	private GameObjects objects[];
	
	//Lien des Images
	private HashMap<String,Image> spriteMap;
	
	Wall wall;

	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param objects
	 */
	public Render(World.TILE[][] world) {
		
		this.world = world;
		
		DH = 11;//11
		DW = 20;//20
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initSpriteMap();
		
		wall = new Wall(world);
		wall.recalculate(0, 0);
		//wall.afficher();
	}
	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param largeur dimension
	 * @param hauteur dimension
	 */
	public Render(World.TILE[][] world, int DW, int DH) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initSpriteMap();
	}
	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param Width dimension
	 * @param Height dimension
	 * @param resolution
	 */
	public Render(World.TILE[][] world, int DW, int DH, int resolution) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initSpriteMap();
	}
	/**
	 * Constructeur
	 * 
	 * @param world
	 * @param Graphical user interface
	 * @param Width dimension
	 * @param Height dimension
	 * @param resolution
	 */
	public Render(World.TILE[][] world, Canvas GUI, int DW, int DH, int resolution) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		this.GUI = GUI;
		
		initSpriteMap();
	}
	
	/**
	 * Remplis de noir le canvas
	 */
	public void clear(){
		
		GUI.getGraphicsContext2D().fillRect(0, 0, DW*RESOLUTION, DH*RESOLUTION);
		
	}
	
	/**
	 * Dessine l'objet
	 * 
	 * @param obj
	 */
	public void draw(GameObjects obj){
		
		GUI.getGraphicsContext2D().save();
		
		Image sprite = getSprite(obj);
		
		if(obj instanceof Player)
		{
			double centerX = cx - (cx - (DW*RESOLUTION)/2);
			double centerY = cy - (cy - (DH*RESOLUTION)/2);
			double playerX = obj.getX() - (cx - (DW*RESOLUTION)/2);
			double playerY = obj.getY() - (cy - (DH*RESOLUTION)/2);
			double translateX = centerX - playerX;
			double translateY = centerY - playerY;
			GUI.getGraphicsContext2D().translate(GUI.getWidth()/2 - translateX,GUI.getHeight()/2 - translateY);
		}
		else
		{
			GUI.getGraphicsContext2D().translate(obj.getX(),obj.getY());//Besoins de monstre pour connaître l'amélioration à faire!
		}
		
		
		
		GUI.getGraphicsContext2D().rotate(obj.getAngle());
		
		GUI.getGraphicsContext2D().drawImage(sprite, -(sprite.getWidth()/2), -(sprite.getHeight()/2));
		
		GUI.getGraphicsContext2D().restore();
	}
	
	/**
	 * Dessine une liste d'objet
	 * 
	 * @param obj[]
	 */
	public void draw(ArrayList<GameObjects> obj){
		
		for(int i = 0; i < obj.size(); i++)
		{
			draw(obj.get(i));
		}
		
	}

	public void drawShadows()
	{
		/*Stop[] stops1 = {new Stop(0.6,Color.TRANSPARENT),new Stop(1.0,Color.BLACK)};
		Stop[] stops2 = {new Stop(0.0,Color.BLACK),new Stop(1.0,Color.BLACK)};
		
		GUI.getGraphicsContext2D().save();
		
		//Ombrage g�n�ral
		//GUI.getGraphicsContext2D().setGlobalAlpha(0.3);
		//GUI.getGraphicsContext2D().fillRect(0, 0, DW*RESOLUTION	,DH*RESOLUTION);
		GUI.getGraphicsContext2D().restore();
		
		
		//Ombrage radial
		GUI.getGraphicsContext2D().setFill(new RadialGradient(30, 0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops1));
		GUI.getGraphicsContext2D().fillOval(0, 0, 1000, 1000);
		GUI.getGraphicsContext2D().restore();
		GUI.getGraphicsContext2D().setFill(new RadialGradient(0, 0, 0, 0, 0.7, true, CycleMethod.NO_CYCLE, stops2));
		//GUI.getGraphicsContext2D().fillOval(0, 0, 1000, 1000);
*/		
		
		
	}
	
	/**
	 * Dessine le monde autour du point (x,y)
	 * 
	 * @param world
	 * @param position x
	 * @param position y
	 */
	public void drawWorld(double x, double y){
		
		//System.out.println("TAILLE ÉCRAN : " + "(" + DW + "," + DH + ")");
		//System.out.println("POSITION : " + "(" + x + "," + y + ")");
		
		cx = x;
		cy = y;

		
		//Screen position
		int spx = (int)(x/RESOLUTION);
		int spy = (int)(y/RESOLUTION);
		
		//System.out.println("POSITION ÉCRAN : " + "(" + spx + "," + spy + ")");
		
		//Starting Case
		int scx = (int)(spx - ((double)DW/2));
		int scy = (int)(spy - ((double)DH/2));
		
		//System.out.println("POSITION CASE DE DÉPART : " + "(" + scx + "," + scy + ")");
		
		//strafe
		
		double sx =  (x - scx*RESOLUTION);
		double sy = (y - scy*RESOLUTION);
		
		//System.out.println("DÉCALAGE : " + "(" + sx + "," + sy + ")");
		
		//System.out.println("POSITION CASE DÉPART SUR CANVAS : " + "(" + (((0 - scx)*RESOLUTION) - sx) + "," + (((0 - scy)*RESOLUTION) - sy )+ ")");
		
		clear();
		
		for(int j = scy; j < (scy + (DH*2)); j++)
		{
			for(int i = scx; i < (scx + (DW*2)); i++)
			{
				
				double scpx = (((i - scx)*RESOLUTION) - sx) + (GUI.getWidth()/2)-RESOLUTION/2;
				double scpy = (((j - scy)*RESOLUTION) - sy) + (GUI.getHeight()/2)-RESOLUTION/2;
				
				//System.out.println("("+ scpx +" " + scpy + ")");
				//System.out.println("("+(((i - scx)*RESOLUTION) - sx)+" " + (((j - scy)*RESOLUTION) - sy));
				
				try
				{
					switch(world[i][j].ordinal())
					{
					
					case 0 :
						break;
					case 1 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/wall.png"), scpx, scpy);
						break;
					case 2 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/stone.png"), scpx, scpy);
						break;
					case 3 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/dirt.png"), scpx, scpy);
						break;
					case 4 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank.png"), scpx, scpy);
						break;
					case 5 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank_alt.png"), scpx, scpy);
						break;
					case 6 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitup.png"), scpx, scpy);
						break;
					case 7 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitdown.png"), scpx, scpy);
						break;
					}
				}catch(ArrayIndexOutOfBoundsException e){}
				
			}
			
		}
		
	}
	
	/**
	 * V�rifie si le sprite existe dans spriteMap, sinon l'ajoute.
	 * 
	 * @param obj
	 * @return L'image du sprite
	 */
	public Image getSprite(GameObjects obj)
	{
		Image sprite = spriteMap.get(obj.getSprite());
		
		if(sprite == null)
		{
			spriteMap.put(obj.getSprite(), new Image(obj.getSprite()));
		}
		
		return sprite;
		
	}
	public GameObjects[] getObjects() {
		return objects;
	}
	public void setObjects(GameObjects[] objects) {
		this.objects = objects;
	}
	/**
	 * 
	 * @return GUI
	 */
	public Canvas getGUI(){
		return GUI;
	}
	/**
	 * 
	 * @param gUI
	 */
	public void setGUI(Canvas gUI) {
		GUI = gUI;
	}
	/**
	 * 
	 * @return DH
	 */
	public int getDH() {
		return DH;
	}
	/**
	 * 
	 * @return DW
	 */
	public int getDW() {
		return DW;
	}
	/**
	 * 
	 * @return RESOLUTION
	 */
	public int getRESOLUTION() {
		return RESOLUTION;
	}
	
	/**
	 * Initialise spriteMap et les tuiles.
	 */
	private void initSpriteMap()
	{
		spriteMap = new HashMap<>();
		
		spriteMap.put("img/wall.png", new Image("img/wall.png"));// WALL
		spriteMap.put("img/stone.png", new Image("img/stone.png"));//ROCK
		spriteMap.put("img/dirt.png", new Image("img/dirt.png"));//CAVE
		spriteMap.put("img/plank.png", new Image("img/plank.png"));//DONJON
		spriteMap.put("img/plank_alt.png", new Image("img/plank_alt.png"));//TUNNEL
		spriteMap.put("img/exitdown.png", new Image("img/exitdown.png"));//Exit down
		spriteMap.put("img/exitup.png", new Image("img/exitup.png"));//Exit up
		spriteMap.put("img/gabriel.png", new Image("img/gabriel.png"));//Exit up
	}
	
	private class Wall
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
		public void recalculate(int x, int y)
		{
			System.out.println("�-"+(x - (DW/2))+" "+(y - (DH/2))+"-�");
			
			afficher((x - (DW/2)),(y - (DH/2)));
			
			System.out.println("WORLD " + world.length +" "+ world[0].length);
			
			
			//Parcoure les cases autour d'un point (x,y)
			for(int j = y - (DH/2);j <= y - (DH/2) + DH;j++)//===================================================================
			{
				for(int i = x - (DW/2);i <= x - (DW/2) + DW;i++)
				{

					try
					{
						if(isWall(i, j))
						{

						
							//V�rifie si elle est seule ou non
							if(isWall(i, j+1) || isWall(i+1, j))
							{
								
							
								if(isWall(i+1, j))//Regarde à droite
								{
									System.out.println(murH.add(new Point[]{new Point(i,j),rightBorder(i+1,j)}));
							
								}
								
								if(isWall(i, j+1))//Regade en dessous (au dessus dans la structure, mais en dessous en mode graphique)
								{

									murV.add(new Point[]{new Point(i,j),underBorder(i,j+1)});
							
								}
							}
							else
							{
								System.out.println(i);
								mur.add( new Point[]{ new Point(i,j), new Point(i,j)});
							
							}
						
						
						}
					
				}catch(ArrayIndexOutOfBoundsException e)
				{
					if(i >= 0 && j >= 0)
					{
						System.out.println("("+i+" "+j+")");
						e.printStackTrace();
					}
				}
			}
			}
			
			deleteRepetitions();
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
		
		public void afficher(int x,int y)
		{
			String affiche = "";

			
				for(int j = y;j < y+DH;j++)

				{
					for(int i = x;i < x + DW;i++)
					{
						
						try{
							if(world[i][j] == TILE.WALL || world[i][j] == TILE.ROCK)
							{
								affiche += "X";
							}
							else
							{
								affiche += "O";
							}
						}catch(IndexOutOfBoundsException e){}

					}
				
				affiche += "\n";
				}

			
			System.out.println(affiche);
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
						}
					}catch(IndexOutOfBoundsException e){}
				}
			}
			
		}
	}
	
	public static void main(String args[]) {
		
		Generator gen = new Generator();

		Render render = new Render(gen.generate());

	}
}
