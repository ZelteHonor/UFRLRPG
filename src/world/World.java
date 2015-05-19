package world;

import java.util.ArrayList;

import pickups.Artefact;
import control.Controller;
/**
 * La Classe world est une instance des diff�rents floors lors d'une partie.
 * @author David Janelle
 * @author David Bourgault
 * @author Gabriel Dion-Bouchard
 * @author Anthony Boudreau
 *
 */
public class World {
	/**
	 * Les types de cases qui compose les floors
	 *
	 */
	public static enum TILE {
		BLACK, WALL, ROCK, CAVE, DONJON, TUNNEL, EXITUP, EXITDOWN
	};

	/**
	 * Le nombre de cases dans un floor
	 */
	public static final int SIZE = 64;
	/**
	 * le nombre d'�tage pr�sent dans le world
	 */
	public static final int LEVEL_COUNT = 3;

	/**
	 * la liste des floors dans le world
	 */
	private ArrayList<Floor> floors;
	
	/**
	 * le floor pr�sentement occuper par le joueur
	 */
	private int current;

	/**
	 * Ce constructeur g�n�re les floors et cr�e la position de l'artefact
	 */
	public World() {
		floors = new ArrayList<Floor>();

		for (int i = 0; i < LEVEL_COUNT; i++)
			floors.add(new Floor(i));

		for (Floor f : floors) {
			if (f.getDepth() == LEVEL_COUNT - 1) {
				generateArtifact(f);
			}
		}

		current = 0;
	}

	/**
	 * Cr�e un instance d'artefact et le place dans le floor envoy� en param�tre
	 * 
	 * @param f le floor sur lequel l'artefact doit �tre cr�e
	 */
	private void generateArtifact(Floor f) {

		Artefact art = new Artefact(f.getEndX(), f.getEndY());
		f.getObjects().add(art);
		f.getTiles()[(int) art.getX()][(int) art.getY()] = TILE.DONJON;
	}

	/**
	 * 
	 * @param depth la profondeur du floor voulu
	 * @return le floor � la profondeur voulu
	 */
	public Floor getFloor(int depth) {
		return floors.get(depth);
	}

	/**
	 * 
	 * @return le floor o� est-ce que le joueur est pr�sentement
	 */
	public Floor getFloor() {
		return floors.get(current);
	}

	/**
	 * Change le floor lorsque le joueur change d'�tage
	 * @param direction d�termine si le joueur monte ou descent d'�tage
	 */
	public void changeFloor(int direction) {
		if (direction + current < 0 || direction + current >= LEVEL_COUNT)
			return;

		current += direction;

		Controller.get().setObjects(floors.get(current).getObjects());
		floors.get(current).setPlayer(Controller.get().getPlayer());
		Controller.get().getRender().setWorld(floors.get(current).getTiles());

		if (direction == -1) {
			Controller.get().getPlayer().setX(floors.get(current).getEndX());
			Controller.get().getPlayer().setY(floors.get(current).getEndY());
		} else {
			Controller.get().getPlayer().setX(floors.get(current).getStartX());
			Controller.get().getPlayer().setY(floors.get(current).getStartY());
		}
	}
	
	/**
	 * reg�n�re les monstres sur tout les �tages sauf l'�tage o� le joueur est pr�sentement situ�e
	 */

	public void regenMonster() {
		for (int i = 0; i < LEVEL_COUNT - 1; i++) {
			floors.get(i).getObjects().clear();
			MonsterGenerator m = new MonsterGenerator();
			m.generateMonster(floors.get(i));
		}
	}
}
