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
		wall.afficher();
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
			GUI.getGraphicsContext2D().translate(GUI.getWidth()/2,GUI.getHeight()/2);
		}
		else
		{
			GUI.getGraphicsContext2D().translate(obj.getX(),obj.getY());
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
		
		//Screen position
		double spx = (x - (DW*RESOLUTION/2));
		double spy = (y - (DH*RESOLUTION/2));
		
		//Starting Case
		int scx = (int)(spx/RESOLUTION);
		int scy = (int)(spy/RESOLUTION);
		
		//strafe
		
		double sx = (spx - scx);
		double sy = (spy - scy);
		
		clear();
		
		for(int j = scy; j < (scy + (DH+2)); j++)
		{
			for(int i = scx; i < (scx + (DW+2)); i++)
			{
				
				try
				{
					switch(world[i][j].ordinal())
					{
					
					case 0 :
						break;
					case 1 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/wall.png"), ((i - scx)*RESOLUTION) - sx, ((j - scy)*RESOLUTION) - sy);
						break;
					case 2 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/stone.png"), ((i - scx)*RESOLUTION) - sx, ((j - scy)*RESOLUTION) - sy);
						break;
					case 3 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/dirt.png"), ((i - scx)*RESOLUTION) - sx, ((j - scy)*RESOLUTION) - sy);
						break;
					case 4 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank.png"), ((i - scx)*RESOLUTION) - sx, ((j - scy)*RESOLUTION) - sy);
						break;
					case 5 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank_alt.png"), ((i - scx)*RESOLUTION) - sx, ((j - scy)*RESOLUTION) - sy);
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
	}
	
	private class Wall
	{
		private World.TILE[][] world;
		private ArrayList<Point[]> mur;
		
		public Wall(World.TILE[][] world) {
			
			this.world = world;
			mur = new ArrayList<Point[]>();
			
		}
		
		public void recalculate(int x, int y)
		{
			System.out.println("�-"+(x - (DW/2))+" "+(y - (DH/2))+"-�");
			
			afficher();
			
			//Parcoure les cases autour d'un point (x,y)
			for(int j = y - (DH/2);j <= y + (DH/2);j++)
			{
				for(int i = x - (DW/2);i <= x + (DW/2);i++)
				{

					try
					{
					//V�rifie si c'est un mur
					if(world[i][j] == TILE.WALL || world[i][j] == TILE.ROCK)
					{
						
						//V�rifie si elle est seule ou non
						if((world[i][j+1] == TILE.WALL || world[i][j+1] == TILE.ROCK) || (world[i+1][j] == TILE.WALL || world[i+1][j] == TILE.ROCK))
						{
							
						
							if(world[i+1][j] == TILE.WALL || world[i+1][j] == TILE.ROCK)
							{
								
								mur.add(new Point[]{new Point(i,j),leftBorder(i+1,j)});
							
							}
						
							if(world[i][j+1] == TILE.WALL || world[i][j+1] == TILE.ROCK)
							{
							
								mur.add(new Point[]{new Point(i,j),underBorder(i,j+1)});
							
							}
						}
						else
						{
							System.out.println(i);
							mur.add( new Point[]{ new Point(i,j), new Point(i,j) });
							
						}
						
						
					}
					
				}catch(ArrayIndexOutOfBoundsException e){}
			}
			}
			
			deleteRepetitions();
			
			for(int i = 0;i < mur.size(); i++)
			{
				System.out.println("("+mur.get(i)[0].x+","+mur.get(i)[0].y+")-("+mur.get(i)[1].x+","+mur.get(i)[1].y+")");
			}
		}
		
		public void afficher()
		{
			String affiche = "";
			
			for(int j = 0;j < 64;j++)
			{
				for(int i = 0;i < 64;i++)
				{
					if(world[i][j] == TILE.WALL || world[i][j] == TILE.ROCK)
					{
						if(mur.contains(new Point(i,j))){
						affiche += "O";}
						else{
						affiche += "�";}
					}
					else
					{
						affiche += "�";
					}
				}
				
				affiche += "\n";
			}
			
			System.out.println(affiche);
		}
		
		private Point leftBorder(int x, int y)
		{
			Point border = new Point(x,y);
			
			if(world[x+1][y] == TILE.WALL || world[x+1][y] == TILE.ROCK)
			{
				border = leftBorder(x+1,y);
			}
			
			return border;
	
		}
		
		private Point underBorder(int x, int y)
		{
			Point border = new Point(x,y);
			
			if(world[x][y+1] == TILE.WALL || world[x][y+1] == TILE.ROCK)
			{
				border = underBorder(x,y+1);
			}
			
			return border;
	
		}
		
		private void deleteRepetitions()
		{
			//� faire
		}
	}
	
	public static void main(String args[]) {
		
		Generator gen = new Generator();

		Render render = new Render(gen.generate());

		
	}
}
