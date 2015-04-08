/**
 * 
 */
package render;

import java.util.ArrayList;
import java.util.HashMap;

import entity.Player;
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
		
		DH = 11;//11
		DW = 20;//20
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
	
	/**
	 * Dessine le monde autour du point (x,y)
	 * 
	 * @param world
	 * @param position x
	 * @param position y
	 */
	public void drawWorld(double x, double y){
		
		/*//System.out.println("TAILLE ÉCRAN : " + "(" + DW + "," + DH + ")");
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
				
				double scpx = (((i - scx)*RESOLUTION) - sx) + (GUI.getWidth()/2);
				double scpy = (((j - scy)*RESOLUTION) - sy) + (GUI.getHeight()/2);
				
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
			
		}*/
		
		clear();
		
		cx = x; cy = y;

		
		double dx = x - (int)x;
		double dy = y - (int)y;
		
		int scx = (int)(x - DW/2);
		int scy = (int)(y - DH/2);
		
		for(int j = scy ; j <= scy + DH + 1 ; j++)
		{
			for(int i = scx; i <= scx + DW + 1; i++)
			{
				
				double px =RESOLUTION*(i-scx);
				double py =RESOLUTION*(j-scy);
				
				try
				{
					switch(world[i][j].ordinal())
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
}
