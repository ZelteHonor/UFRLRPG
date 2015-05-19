package world;

import java.awt.Point;
import java.util.ArrayList;
import world.World.TILE;
import entity.Monster;

public class MonsterGenerator {

	// liste de noms pour les monstres
	public static final String[] NAMES = { "Alpha", "Bravo", "Charlie",
			"Delta", "Echo", "Foxtrot", "Golf", "Hotel", "India", "Juliett" };

	public static final String[] IMAGES = { "spider_green", "spider_grey",
			"spider_purple", "spider_red", "zombie" };

	private ArrayList<Point> locationList;

	/** Constructeur par d�faut */
	public MonsterGenerator() {
		locationList = new ArrayList<Point>();
	}

	/**
	 * Constructeur
	 * 
	 * @param Floor
	 */
	public void generateMonster(Floor f) {

		// génère le nombre d'enemmi dans le floor present
		int count = createCount(f);
		createLocationList(f);

		// génère les monstres avec des attributs aléatoire et on les mets
		// dans la liste
		for (int i = 0; i < count; i++) {
			Point point = chooseLocation();

			String sprite = chooseImage();

			Monster m;
			if (sprite.contains("zombie"))
				m = new Monster(point.getX() + 0.5, point.getY() + 0.5, 20, 10,
						10, 0.03f);
			else
				m = new Monster(point.getX() + 0.5, point.getY() + 0.5, 10, 2,
						5, 0.09f);
			m.setName(chooseName());
			m.setSprite(sprite);
			f.getObjects().add(m);
		}

	}

	/**
	 * 
	 * @return String représentant l'URL aléatiorement chisit dans la banque
	 *         d'image alloué à cette excercice "définir quelle banque utiliser"
	 */
	private String chooseImage() {
		return "img/" + IMAGES[(int) (Math.random() * IMAGES.length)] + ".png";

	}

	/**
	 * 
	 * @return Nom aléatoire sorti d'une banque de noms
	 */
	private String chooseName() {
		return NAMES[(int) (Math.random() * NAMES.length)];
	}

	/**
	 * ajoute � une liste toutes les positions possible pour qu'un monstre
	 * appara�t
	 * 
	 * @param Floor
	 */
	private void createLocationList(Floor f) {
		for (int i = 0; i < World.SIZE; i++)
			for (int j = 0; j < World.SIZE; j++)
				if ((f.getTiles()[i][j] == TILE.DONJON
						|| f.getTiles()[i][j] == TILE.CAVE || f.getTiles()[i][j] == TILE.TUNNEL)
						&& Math.sqrt(Math.pow(i - f.getStartX(), 2)
								+ Math.pow(j - f.getStartY(), 2)) >= 20)
					locationList.add(new Point(i, j));
	}

	/**
	 * choisit un point dans la liste de position possible pour les monstres et
	 * l'enl�ve de la liste pour emp�cher que les monstre soit cr�e plusieurs
	 * fois sur le m�me point
	 * 
	 * @return un point dans la liste
	 */
	private Point chooseLocation() {
		Point point = locationList.get((int) (Math.random() * locationList
				.size()));
		locationList.remove(point);
		return point;
	}

	/**
	 * @param Floor
	 * 
	 * @return 20 + 5 * "numéro de l'étage"
	 */
	private int createCount(Floor f) {
		return 30 + 10 * f.getDepth();
	}

}
