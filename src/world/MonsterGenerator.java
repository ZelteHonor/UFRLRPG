package world;

import java.awt.Point;
import java.util.ArrayList;

import world.World.TILE;
import javafx.scene.image.Image;
import entity.Monster;

public class MonsterGenerator {

	// liste de noms pour les monstres
	public static final String[] NAMES = { "Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliett" };

	public static final String[] IMAGES = { "img/spider.png" };

	private ArrayList<Point> locationList;
	
	public MonsterGenerator() {
		locationList = new ArrayList<Point>();
	}
	
	public void generateMonster(Floor f) {

		// génère le nombre d'enemmi dans le floor present
		int count = createCount(f);
		createLocationList(f);

		// génère les monstres avec des attributs aléatoire et on les mets
		// dans la liste
		for (int i = 0; i < count; i++) {
			Point point = chooseLocation();

			Monster m = new Monster(point.getX() + 0.5, point.getY() + 0.5, 10, null);
			m.setName(chooseName());
			m.setSprite(chooseImage());
			f.getObjects().add(m);
		}

	}

	/**
	 * 
	 * @return String représentant l'URL aléatiorement chisit dans la banque
	 *         d'image alloué à cette excercice
	 *         "définir quelle banque utiliser"
	 */
	private String chooseImage() {
		return IMAGES[(int) (Math.random() * IMAGES.length)];

	}

	/**
	 * 
	 * @return Nom aléatoire sorti d'une banque de noms
	 */
	private String chooseName() {
		return NAMES[(int) (Math.random() * NAMES.length)];
	}

	/**
	 * 
	 * @return a number within 7, 11, 12 and 15
	 */
	private void createLocationList(Floor f) {
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++) 
				if ((f.getTiles()[i][j] == TILE.DONJON || f.getTiles()[i][j] == TILE.CAVE || f.getTiles()[i][j] == TILE.TUNNEL)
					&& Math.sqrt(Math.pow(i - f.getStartX(), 2) + Math.pow(j - f.getStartY(), 2)) >= 20)
					locationList.add(new Point(i,j));	
	}
	
	private Point chooseLocation() {
		Point point = locationList.get((int) (Math.random() * locationList.size()));
		locationList.remove(point);
		return point;
	}

	/**
	 * 
	 * @return 20 + 5 * "numéro de l'étage"
	 */
	private int createCount(Floor f) {
		return 20 + 5 * f.getDepth();
	}

}
