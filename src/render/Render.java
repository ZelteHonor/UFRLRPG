/**
 * 
 */
package render;

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
	 * Constructeur
	 * 
	 * @param world[]
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
		
		initLinks();
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
		
		initLinks();
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
		tileLink = new String[5];
		tileLink[0] = "/Ovo64/wall.png";// WALL
		tileLink[1] = "/Ovo64/stone.png";//ROCK
		tileLink[2] = "/Ovo64/dirt.png";//CAVE
		tileLink[3] = "/Ovo64/plank.png";//DONJON
		tileLink[4] = "/Ovo64/plank_alt.png";//TUNNEL
	}
	
	//TEMPORAIRE____POUR_TESTS
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}

}
