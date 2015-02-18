/**
 * 
 */
package render;

import java.util.Objects;
import gameObjects.GameObjects;
import world.World;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;

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
	
	//Dimension et résolution d'affichage
	private final int DH;
	private final int DW;
	private final int RESOLUTION;

	//À afficher
	private World.TILE[][] world;
	private GameObjects objects[];

	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 */
	public Render(World.TILE[][] world, GameObjects objects[]) {
		
		this.world = world;
		this.objects = objects;
		
		DH = 11;
		DW = 20;
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		
	}
	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 * @param largeur dimension
	 * @param hauteur dimension
	 */
	public Render(World.TILE[][] world, GameObjects objects[], int DW, int DH) {
		this.world = world;
		this.objects = objects;
		
		this.DH = DH;
		this.DW = DW;
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
	}
	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 * @param Width dimension
	 * @param Height dimension
	 * @param resolution
	 */
	public Render(World.TILE[][] world, GameObjects objects[], int DW, int DH, int resolution) {
		this.world = world;
		this.objects = objects;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
	}
	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 * @param Graphical user interface
	 * @param Width dimension
	 * @param Height dimension
	 * @param resolution
	 */
	public Render(World.TILE[][] world, GameObjects objects[], Canvas GUI, int DW, int DH, int resolution) {
		this.world = world;
		this.objects = objects;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		this.GUI = GUI;
	}
	
	/**
	 * Fill with black the GUI.
	 */
	public void clear(){
		
		GUI.getGraphicsContext2D().setFill(Color.BLACK);
		GUI.getGraphicsContext2D().fill();
		
	}
	
	public void draw(GameObjects obj){
		
		GUI.getGraphicsContext2D().drawImage(obj.getSprite(), obj.getX(), obj.getY());
		
	}
	
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
	public void draw(World.TILE[][] world,double x, double y){
		String matriceTemp = "";
		
		//Screen position
		double spx = (x - (DW*RESOLUTION/2));
		double spy = (y - (DH*RESOLUTION/2));
		
		//Starting Case
		int scx = (int)(spx/RESOLUTION);
		int scy = (int)(spy/RESOLUTION);
		
		System.out.println(spx + " " + spy);
		System.out.println(scx + " " + scy);
		
		for(int j = scy; j < (scy + (DH+2)); j++)
		{
			for(int i = scx; i < (scx + (DW+2)); i++)
			{
				
				
				if(i >= 0 && j >= 0)
				{
					matriceTemp += " " + world[i][j].toString();
				}
			}
			
			matriceTemp += "\n";
			
		}
		
		System.out.println(matriceTemp);
		
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
	
	
	
	//TEMPORAIRE____POUR_TESTS
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		World.TILE[][] world = new World.TILE[World.SIZE][World.SIZE];
		
		for(int i = 0; i < 128; i++)
		{
			
			for(int j = 0; j < 128; j++)
			{
				
				world[i][j] = World.TILE.DONJON;
				
			}
			
		}
		
		
		world[0][0] = World.TILE.CAVE;
		
		world[0][0] = World.TILE.CAVE;
		Render test = new Render(world,null);
		test.draw(world, 0*64, 0*64);

	}

}
