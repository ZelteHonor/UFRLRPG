/**
 * 
 */
package render;

import java.util.Objects;

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
	
	//Dimension et résolution d'affichage
	private final int DH;
	private final int DW;
	private final int RESOLUTION;

	//À afficher
	private World.TILE[][] world;
	private GameObjects objects[];
	
	//Lien des TILE
	private String tileLink[];

	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 */
	public Render(World.TILE[][] world) {
		
		this.world = world;
		
		DH = 11;
		DW = 20;
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initLinks();
	}
	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 * @param largeur dimension
	 * @param hauteur dimension
	 */
	public Render(World.TILE[][] world, int DW, int DH) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		RESOLUTION = 64;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initLinks();
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
	public Render(World.TILE[][] world, int DW, int DH, int resolution) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		
		initLinks();
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
	public Render(World.TILE[][] world, Canvas GUI, int DW, int DH, int resolution) {
		this.world = world;
		
		this.DH = DH;
		this.DW = DW;
		this.RESOLUTION = resolution;
		
		this.GUI = GUI;
		
		initLinks();
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
						GUI.getGraphicsContext2D().drawImage(new Image(tileLink[0]), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						System.out.println(new Image(tileLink[0]));
						break;
					case 2 :
						GUI.getGraphicsContext2D().drawImage(new Image(tileLink[1]), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						System.out.println(new Image(tileLink[1]));
						break;
					case 3 :
						GUI.getGraphicsContext2D().drawImage(new Image(tileLink[2]), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						System.out.println(new Image(tileLink[2]));
						break;
					case 4 :
						GUI.getGraphicsContext2D().drawImage(new Image(tileLink[3]), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						System.out.println(new Image(tileLink[3]));
						break;
					case 5 :
						GUI.getGraphicsContext2D().drawImage(new Image(tileLink[4]), (i - scx)*RESOLUTION, (j - scy)*RESOLUTION);
						System.out.println(new Image(tileLink[4]));
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
	 * C'est ici que ce trouve les liens des images des tuiles.
	 */
	private void initLinks()
	{
		tileLink = new String[6];
		tileLink[0] = "/action_drawhealth.gif";
		tileLink[1] = "/action_gravity.gif";
		tileLink[2] = "/action_ifcollision.gif";
		tileLink[3] = "/action_ifquestion.gif";
		tileLink[4] = "/action_ifscore.gif";
		tileLink[5] = "/action_vreverse.gif";
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
				
				//world[i][j] = World.TILE.DONJON;
				
			}
			
		}
		
		
		world[0][0] = World.TILE.CAVE;
		
		world[0][0] = World.TILE.CAVE;
		Render test = new Render(world);
		test.drawWorld(0*64, 0*64);

	}

}
