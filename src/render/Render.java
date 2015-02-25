/**
 * 
 */
package render;

import java.util.HashMap;
import java.util.Map;

import gameObjects.GameObjects;
import world.World;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;

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

	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param objects
	 */
	public Render(World.TILE[][] world) {
		
		this.world = world;
		
		DH = 30;//11
		DW = 30;//20
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initSpriteMap();
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
		GUI.getGraphicsContext2D().rotate(25);
		
		//int[] temp = changeCoordinate(obj.getX(),obj.getY(),0);
		
		double c = Math.sqrt(obj.getX()*obj.getX() + obj.getX()*obj.getX());
		
		System.out.println("("+obj.getX()+","+obj.getY()+")");
		//System.out.println("("+temp[0]+","+temp[1]+")");
		System.out.println("("+c*Math.sin(90-25)+","+c*Math.cos(90-25)+")");
		
		
		
		GUI.getGraphicsContext2D().drawImage(getSprite(obj), c*Math.sin(90-25), c*Math.cos(90-25));
		
		GUI.getGraphicsContext2D().restore();
	}
	
	/**
	 * Ajuste la position d'une coordonn�e
	 * 
	 * @param x
	 * @param y
	 * @param angle
	 * @return nouvelles positions
	 */
	public int[] changeCoordinate(int x,int y,double theta)
	{
		//distance points/centre
		double c = Math.sqrt((x*x)+(y*y));
		
		//angle de la position initiale
		double alpha = Math.atan(y/x);
		
		//angle entre le point initiale et le point finale
		double beta = theta - alpha;
		
		//La Translation � �ffectuer
		double DeltaX = c - c*Math.cos(beta);
		double DeltaY = c - c*Math.sin(beta);
		
		int xFinale = (int) (c - DeltaX);
		int yFinale = (int) (DeltaY);
		
		return new int[]{xFinale,yFinale};
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
	
	/**
	 * Dessine une liste d'objet
	 * 
	 * @param obj[]
	 */
	public void draw(GameObjects obj[]){
		
		for(int i = 0; i < obj.length; i++)
		{
			draw(obj[i]);
		}
		
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
		
		clear();
		
		
		for(int j = scy; j < (scy + (DH+2)); j++)
		{
			for(int i = scx; i < (scx + (DW+2)); i++)
			{
				System.out.println(world[i][j].ordinal());
				
				try
				{
					switch(world[i][j].ordinal())
					{





					
					case 0 :
						break;
					case 1 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/wall.png"), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						break;
					case 2 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/stone.png"), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						break;
					case 3 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/dirt.png"), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						break;
					case 4 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank.png"), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						break;
					case 5 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank_alt.png"), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						break;
					}
				}catch(ArrayIndexOutOfBoundsException e){}
				
			}
			
		}
		
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
}
