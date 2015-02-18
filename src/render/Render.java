/**
 * 
 */
package render;

import java.util.Objects;

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
	
	//Graphical interface
	private Canvas GUI;
	
	//Dimension et resolution of screen
	private final int DH;
	private final int DW;
	private final int RESOLUTION;

	//To pin up
	private Tile world[][];
	private Objects objects[];

	/**
	 * Constructor
	 * 
	 * @param world
	 * @param objects
	 */
	public Render(Tile world[][],Objects objects[]) {
		
		this.world = world;
		this.objects = objects;
		
		DH = 10;
		DW = 15;
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
	 */
	public Render(Tile world[][],Objects objects[],int DW,int DH) {
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
	public Render(Tile world[][],Objects objects[],int DW,int DH,int resolution) {
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
	public Render(Tile world[][],Objects objects[],Canvas GUI,int DW,int DH,int resolution) {
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
	
	public void draw(Objects obj){
		
		GUI.getGraphicsContext2D().drawImage(obj/*.getBLABLABLA(image)*/, obj.getX(), obj.getY);//++++++++++++++++++++++++++++++++++++++++AJUSTEMENT ICI
		
	}
	
	public void draw(Objects obj[]){
		
		for(int i = 0; i < obj.length; i++)
		{
			draw(obj[i]);
		}
		
	}
	
	/**
	 * Draw the world from the center of coordinate (x,y)
	 * 
	 * @param world
	 * @param position x
	 * @param position y
	 */
	public void draw(Tile world[][],double x, double y){
		
		//Screen position
		double spx = (x - (DW*RESOLUTION/2));
		
		double spy = (y - (DH*RESOLUTION/2));
		
		for(int i = (int)(spx/RESOLUTION); i < (DW+1); i++)
		{
			for(int j = (int)(spy/RESOLUTION); j < (DH+1); j++)
			{
				
			}
		}
		
	}
	
	public Canvas getGUI(){
		return GUI;
	}
	public void setGUI(Canvas gUI) {
		GUI = gUI;
	}
	public int getDH() {
		return DH;
	}
	public int getDW() {
		return DW;
	}
	public int getRESOLUTION() {
		return RESOLUTION;
	}
	//TEMPORAIRE____POUR_TESTS
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
