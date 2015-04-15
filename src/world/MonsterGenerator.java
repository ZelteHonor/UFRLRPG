package world;

import java.awt.Point;
import java.util.ArrayList;

import world.World.TILE;
import javafx.scene.image.Image;
import entity.Monster;

public class MonsterGenerator {

	// liste de noms pour les monstres
	public static final String[] NAMES = { "charlie", "alpha", "bravo",
			"delta", "epsilon", "Zeta", "Omega", "Zulu", "theta", "Quebec" };

	public static final String[] IMAGES = { "goblin.png", "skeleton.png",
			"ooze.png", "witch.png", "spider.png" };

	public ArrayList<Monster> generateMonster(Floor f) {

		ArrayList<Monster> monsters = new ArrayList<Monster>();

		// génère le numbre d'enemie dans le floor prsent
		int count = createCount(f);

		// génère les monstres avec des attributs aléatoire et on les mets
		// dans la liste
		for (int i = count; i >= 0; i--) {
			Point p = new Point();
			
			p = createLoc(f);
			
			Monster m = new Monster(p.getX(), p.getY(), 10,null);

			m.setName(createName());

			m.setSprite(/*createImage()*/"gabriel.png");
			monsters.add(m);
		}

		return null;

	}

	/**
	 * 
	 * @return String représentant l'URL aléatiorement chisit dans la banque
	 *         d'image alloué à cette excercice "définir quelle banque utiliser"
	 */
	private String createImage() {
		String image = "gabriel.png";

		// Math.random() donne un double entre 0 et 1, ce chiffre est multiplié
				// par 5 pour représenté les 5 choix de la liste. on soustrait 1
				// considérant que le premier élément de la liste est 0 et le dernier
				// (dans ce cas) est 4
				//image = IMAGES[(int) (Math.random() * 5) - 1];
		return image;

	}

	/**
	 * 
	 * @return Nom aléatoire sorti d'une banque de noms
	 */
	private String createName() {
		String name = "default name";

		// Math.random() donne un double entre 0 et 1, ce chiffre est multiplié
		// par 10 pour représenté les 10 choix de la liste. on soustrait 1
		// considérant que le premier élément de la liste est 0 et le dernier
		// (dans ce cas) est 9
		//name = NAMES[(int) (Math.random() * 10) - 1];

		return name;

	}

	/**
	 * 
	 * @return a number within 7, 11, 12 and 15
	 */
	private Point createLoc(Floor f) {
		
		Point s = new Point();

		double r1 = Math.random() * World.SIZE;
		double r2 = Math.random() * World.SIZE;
		
		while(!(f.getTiles()[(int)r1][(int)r2].equals(TILE.DONJON)))
		{
			r1 = Math.random() * World.SIZE;
			r2 = Math.random() * World.SIZE;
		}
		s.setLocation(r1, r2);
		return s;
	}

	/**
	 * 
	 * @return 20 + 5 * "numéro de l'étage"
	 */
	private int createCount(Floor f) {
		return 20 + 5 * f.getDepth();
	}

}
