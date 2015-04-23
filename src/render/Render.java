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
		/* Canvas */
	private Canvas GUI;
	
	/* Display size and resolution */
	public static final int DH = 11;
	public static final int DW = 20;
	public static final int RESOLUTION = 64;
	
	/* Camera */
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
		
		loadSprites();
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
		
		GUI.getGraphicsContext2D().drawImage(sprite, - (sprite.getWidth()/2), - (sprite.getHeight()/2));
		
		GUI.getGraphicsContext2D().restore();
	}
	
	/**
	 * Dessine une liste d'objet
	 * 
	 * @param obj[]
	 */
	public void draw(ArrayList<GameObjects> obj){	
		for(int i = 0; i < obj.size(); i++)
			draw(obj.get(i));		
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
		
		cx = x; 
		cy = y;

		//décalage
		double dx = x - (int)x;
		double dy = y - (int)y;//trouver pourquoi
		//System.out.println("("+dx+" "+dy+")");
		
		int scx;
		int scy;
		
		//Case de départ haut/gauche
		if(x >= 0)
			scx = (int)Math.floor(x - DW/2);
		else
			scx = (int)Math.floor(x - DW/2 + 1);
		if(y >= 0)
			scy = (int)Math.floor(y - DH/2);
		else
			scy = (int)Math.floor(y - DH/2 + 1);
				
		for(int j = -1 ; j <= DH + 1 ; j++)	{
			for(int i = -1; i <= DW + 1; i++) {		
				//Position de l'image
				double px = (-dx + i)*RESOLUTION;
				double py = (-dy + j)*RESOLUTION;
				
				try {
					switch(world[scx+i][scy+j]) {
					case WALL :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/wall.png"), px, py); break;
					case ROCK :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/stone.png"), px, py); break;
					case CAVE :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/dirt.png"), px, py); break;
					case DONJON :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank.png"), px, py); break;
					case TUNNEL :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/plank_alt.png"), px, py); break;
					case EXITUP :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitup.png"), px, py); break;
					case EXITDOWN :
						GUI.getGraphicsContext2D().drawImage(spriteMap.get("img/exitdown.png"), px, py); break;
					case BLACK :
					default:
						break;
					}
				} catch(ArrayIndexOutOfBoundsException e) {}
			}
			
		}
	}
	
	/**
	 * Vérifie si le sprite existe dans spriteMap, sinon l'ajoute.
	 * 
	 * @param obj
	 * @return L'image du sprite
	 */
	public Image getSprite(GameObjects obj)
	{
		return spriteMap.get(obj.getSprite());	
	}
	
	public GameObjects[] getObjects() {
		return objects;
	}
	
	public void setObjects(GameObjects[] objects) {
		this.objects = objects;
	}
	
	public void setWorld(World.TILE[][] world) {
		this.world = world;
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
	 * @param gui, le nouveau canvas
	 */
	public void setGUI(Canvas gui) {
		GUI = gui;
	}

	
	/**
	 * Initialise spriteMap et les tuiles.
	 */
	private void loadSprites()
	{
		spriteMap = new HashMap<>();
		
		/* Donjon */
		spriteMap.put("img/wall.png", new Image("img/wall.png"));
		spriteMap.put("img/stone.png", new Image("img/stone.png"));
		spriteMap.put("img/dirt.png", new Image("img/dirt.png"));
		spriteMap.put("img/plank.png", new Image("img/plank.png"));
		spriteMap.put("img/plank_alt.png", new Image("img/plank_alt.png"));
		spriteMap.put("img/exitdown.png", new Image("img/exitdown.png"));
		spriteMap.put("img/exitup.png", new Image("img/exitup.png"));
		
		/* GameObjects */
		spriteMap.put("img/player.png", new Image("img/player.png"));
		spriteMap.put("img/spider.png", new Image("img/spider.png"));
		spriteMap.put("img/arrow.png", new Image("img/arrow.png"));

	}
}
