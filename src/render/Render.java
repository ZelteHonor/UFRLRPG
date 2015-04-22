/**
 * 
 */
package render;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import entity.Player;
import gameObjects.GameObjects;
import gameObjects.Projectile;
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
	private final int DH = 11;
	private final int DW = 20;
	private final int RESOLUTION = 64;
	
	private double cx = 0;
	private double cy = 0;

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
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initSpriteMap();

	}
	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param largeur dimension
	 * @param hauteur dimension
	 *//*
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
	 *//*
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
	 *//*
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
		
		double refx = cx - DW/2;
		double refy = cy - DH/2;
		
		double px = (obj.getX() - refx)*RESOLUTION;
		double py = (obj.getY() - refy)*RESOLUTION;

		GUI.getGraphicsContext2D().translate(px,py);//Besoins de monstre pour connaître l'amélioration à faire!	
		
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
	
	/**
	 * Dessine le monde autour du point (x,y)
	 * 
	 * @param world
	 * @param position x
	 * @param position y
	 */
	public void drawWorld(double x, double y){
		
		clear();
		
		cx = x; cy = y;

		//décalage
		double dx = x - (int)x;
		double dy = y - (int)y;//trouver pourquoi
		//System.out.println("("+dx+" "+dy+")");
		
		int scx;
		int scy;
		
		//Case de départ haut/gauche
		if(x >= 0)
		{
			scx = (int)Math.floor(x - DW/2);
		}
		else
		{
			scx = (int)Math.floor(x - DW/2 + 1);
		}
		if(y >= 0)
		{
			scy = (int)Math.floor(y - DH/2);
		}
		else
		{
			scy = (int)Math.floor(y - DH/2 + 1);
		}
		
		
		
		//System.out.println("("+scx+" "+scy+")");
		
		for(int j = -1 ; j <= DH + 1 ; j++)
		{
			for(int i = -1; i <= DW + 1; i++)
			{
				
				//Position de l'image
				double px = (-dx + i)*RESOLUTION;
				double py = (-dy + j)*RESOLUTION;
				
				//System.out.println("("+px+" "+py+")");
				
				try
				{
					switch(world[scx+i][scy+j].ordinal())
					{
					
					case 0 :
						break;
					case 1 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/wall.png"), px, py);
						break;
					case 2 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/stone.png"), px, py);
						break;
					case 3 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/dirt.png"), px, py);
						break;
					case 4 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank.png"), px, py);
						break;
					case 5 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank_alt.png"), px, py);
						break;
					case 6 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitup.png"), px, py);
						break;
					case 7 :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitdown.png"), px, py);
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
		
		if(!spriteMap.containsKey(obj.getSprite()))
		{
			spriteMap.put(obj.getSprite(), new Image(obj.getSprite()));
			sprite = spriteMap.get(obj.getSprite());
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
	}
}
