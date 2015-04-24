/**
 * 
 */
package control;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

import entity.Monster;
import entity.Player;
import gameobject.GameObject;
import weapons.Arrow;
import weapons.Bow;
import weapons.Sword;
import world.World;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

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
	private GraphicsContext gc;
	
	/* Display size and resolution */
	public static final int DH = 11;
	public static final int DW = 20;
	public static final int RESOLUTION = 64;
	
	/* Camera */
	private double cx = 0;
	private double cy = 0;

	//� afficher
	private World.TILE[][] world;
	private GameObject objects[];
	
	//Lien des Images
	private HashMap<String,Image> spriteMap;
	private Font f04b03;

	/**
	 * Constructeur
	 * 
	 * @param world[]
	 * @param objects
	 */
	public Render(World.TILE[][] world) {
		
		this.world = world;
		GUI = new Canvas(DW*RESOLUTION, DH*RESOLUTION);
		gc = GUI.getGraphicsContext2D();
		loadSprites();
		
		f04b03 = Font.loadFont(getClass().getResourceAsStream("/img/04b03.ttf"),16);
	}
	
	/**
	 * Remplis de noir le canvas
	 */
	public void clear(){
		gc.setFill(Color.BLACK);
		gc.fillRect(0, 0, DW*RESOLUTION, DH*RESOLUTION);
	}
	
	/**
	 * Dessine l'objet
	 * 
	 * @param obj
	 */
	public void draw(GameObject obj){
		
		gc.save();
		
		Image sprite = getSprite(obj);
		
		double refx = cx - DW/2;
		double refy = cy - DH/2;
		
		double px = (obj.getX() - refx)*RESOLUTION;
		double py = (obj.getY() - refy)*RESOLUTION;

		gc.translate(px,py);//Besoins de monstre pour connaître l'amélioration à faire!	
		
		if (obj instanceof Sword)
			gc.rotate(Math.toDegrees(obj.getAngle())+((Sword)obj).getAngleDiff());
		else
			gc.rotate(Math.toDegrees(obj.getAngle()));
				
		gc.drawImage(sprite, - (sprite.getWidth()/2), - (sprite.getHeight()/2));
		gc.restore();
	}
	
	/**
	 * Dessine une liste d'objet
	 * 
	 * @param obj[]
	 */
	public void draw(ArrayList<GameObject> obj){	
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
						gc.drawImage(spriteMap.get("img/wall.png"), px, py); break;
					case ROCK :
						gc.drawImage(spriteMap.get("img/stone.png"), px, py); break;
					case CAVE :
						gc.drawImage(spriteMap.get("img/dirt.png"), px, py); break;
					case DONJON :
						gc.drawImage(spriteMap.get("img/plank.png"), px, py); break;
					case TUNNEL :
						gc.drawImage(spriteMap.get("img/plank_alt.png"), px, py); break;
					case EXITUP :
						gc.drawImage(spriteMap.get("img/exitup.png"), px, py); break;
					case EXITDOWN :
						gc.drawImage(spriteMap.get("img/exitdown.png"), px, py); break;
					case BLACK :
					default:
						break;
					}
				} catch(ArrayIndexOutOfBoundsException e) {}
			}
			
		}
	}
	
	
	public void drawHUD() {
		Player player = Controller.get().getPlayer();
		if (player.getHealth() < 0)
			player.setHealth(0);
		
		/* Monster healthbar */
		for (GameObject o : Controller.get().getObjects()) {
			if (o instanceof Monster) {
				Monster m = (Monster) (o);
				if (m.getHealth() != m.getMaxHealth()) {
					gc.setFill(Color.RED);
					gc.fillRect((m.getX()-Controller.get().getCX())*RESOLUTION + 640 - 8, (m.getY()-Controller.get().getCY())*RESOLUTION + 360 - 64,16*(m.getHealth()/(m.getMaxHealth()*1.0)),4);
					gc.setFill(Color.DARKRED);
					gc.fillRect((m.getX()-Controller.get().getCX())*RESOLUTION + 641 - 8, (m.getY()-Controller.get().getCY())*RESOLUTION + 361 - 64,16*(m.getHealth()/(m.getMaxHealth()*1.0)),4);
					
					gc.setFill(Color.DARKGREY);
					gc.fillRect((m.getX()-Controller.get().getCX())*RESOLUTION + 640 - 8 + 16*(m.getHealth()/(m.getMaxHealth()*1.0)), (m.getY()-Controller.get().getCY())*RESOLUTION + 360 - 64,16 - 16*(m.getHealth()/(m.getMaxHealth()*1.0)),4);
					gc.setFill(Color.GREY);
					gc.fillRect((m.getX()-Controller.get().getCX())*RESOLUTION + 641 - 8 + 16*(m.getHealth()/(m.getMaxHealth()*1.0)), (m.getY()-Controller.get().getCY())*RESOLUTION + 361 - 64,16 - 16*(m.getHealth()/(m.getMaxHealth()*1.0)),4);
				}
			}
		}
		
		/* Player healthbar */
		gc.setFill(Color.DARKRED);
		gc.fillRect(514, 658, (player.getHealth()/100.0) * 256.0, 16);
		gc.setFill(Color.GREY);
		gc.fillRect(514 + (player.getHealth()/100.0) * 256.0, 658, 256 - (player.getHealth()/100.0) * 256.0, 16);
		
		gc.setFill(Color.RED);
		gc.fillRect(512, 656, (player.getHealth()/100.0) * 256.0, 16);
		gc.setFill(Color.DARKGREY);
		gc.fillRect(512 + (player.getHealth()/100.0) * 256.0, 656, 256 - (player.getHealth()/100.0) * 256.0, 16);
		
		gc.setFill(Color.WHITE);
		gc.setFont(f04b03);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.fillText(Integer.toString(player.getHealth()), 640, 670);
		
		/* Cursor */
		if (player.getWeapon() instanceof Bow)
			gc.drawImage(getSprite("img/crosshair.png"), Controller.get().getInput().getMouse().getSceneX() - 16, Controller.get().getInput().getMouse().getSceneY() - 16);
		
		/* Mort */
		if (player.getHealth() == 0) {
			gc.drawImage(spriteMap.get("img/deathscreen.png"), 0, 0);
			gc.fillText("Appuyer Espace pour rejouer", 640, 500);
		}
	}
	/**
	 * Vérifie si le sprite existe dans spriteMap, sinon l'ajoute.
	 * 
	 * @param obj
	 * @return L'image du sprite
	 */
	public Image getSprite(GameObject obj) {
		return spriteMap.get(obj.getSprite());	
	}
	public Image getSprite(String img) {
		return spriteMap.get(img);	
	}
	
	public GameObject[] getObjects() {
		return objects;
	}
	
	public void setObjects(GameObject[] objects) {
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
		
		/* Cursor */
		spriteMap.put("img/cursor.png", new Image("img/cursor.png"));
		spriteMap.put("img/crosshair.png", new Image("img/crosshair.png"));
		
		/* Donjon */
		spriteMap.put("img/wall.png", new Image("img/wall.png"));
		spriteMap.put("img/stone.png", new Image("img/stone.png"));
		spriteMap.put("img/dirt.png", new Image("img/dirt.png"));
		spriteMap.put("img/plank.png", new Image("img/plank.png"));
		spriteMap.put("img/plank_alt.png", new Image("img/plank_alt.png"));
		spriteMap.put("img/exitdown.png", new Image("img/exitdown.png"));
		spriteMap.put("img/exitup.png", new Image("img/exitup.png"));
		
		/* Player */
		spriteMap.put("img/player.png", new Image("img/player.png"));
		spriteMap.put("img/playerdead.png", new Image("img/playerdead.png"));
		spriteMap.put("img/arrow.png", new Image("img/arrow.png"));
		spriteMap.put("img/bow.png", new Image("img/bow.png"));
		spriteMap.put("img/sword.png", new Image("img/sword.png"));
		
		/* Monster */
		spriteMap.put("img/spider_green.png", new Image("img/spider_green.png"));
		spriteMap.put("img/spider_grey.png", new Image("img/spider_grey.png"));
		spriteMap.put("img/spider_purple.png", new Image("img/spider_purple.png"));
		spriteMap.put("img/spider_red.png", new Image("img/spider_red.png"));
		spriteMap.put("img/zombie.png", new Image("img/zombie.png"));
		
		/* Death*/
		switch((int) (Math.random()*6)) {
		case 0: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_01.png")); break;
		case 1: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_02.png")); break;
		case 2: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_03.png")); break;
		case 3: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_04.png")); break;
		case 4: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_05.png")); break;
		case 5: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_06.png")); break;
		case 6: spriteMap.put("img/deathscreen.png", new Image("img/deathscreen_07.png")); break;
		}
	}
}
