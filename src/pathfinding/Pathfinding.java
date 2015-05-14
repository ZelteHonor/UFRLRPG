package pathfinding;

import java.awt.Point;
import java.util.HashSet;
import java.util.Collections;

import world.Floor;
import world.World;

/**
 * Classe de PathFinding pour les monstres
 * @author gabriel
 */
public class Pathfinding {

	/**
	 * A* search algorithm.
	 * @param start
	 * 	Point de départ
	 * @param end
	 * 	Point d'arriver
	 * @param floor
	 * 	L'étage courant
	 * @return
	 * 	Retourne une suite de node avec le précédent attacher a la queue.
	 */
	public static Node getPath(Point start, Point end, Floor floor) {
		int searchLimit = 200;
		HashSet<Node> openList = new HashSet<Node>();
		HashSet<Node> closedList = new HashSet<Node>();
		Node current = new Node(start, null, 0f, (float) start.distance(end));
		closedList.add(current);
		//Take all valid node around the start
		for (int i = (int) (start.getX() - 1); i <= start.getX() + 1; i++) {
			for (int j = (int) (start.getY() - 1); j <= start.getY() + 1; j++) {
				if (i != start.getX() || j != start.getY()) {
					Point temp = new Point(i, j);
					if (floor.getTiles()[i][j] != World.TILE.WALL
							&& floor.getTiles()[i][j] != World.TILE.ROCK) {
						openList.add(new Node(temp, null, (float) temp
								.distance(start), (float) temp.distance(end)));
					}
				}
			}
			
			
		}
		//System.out.println("Start");

		while (!current.getCoor().equals(end) && searchLimit >= 0) {
			//The current Node is the one with the lowest cost
			current = Collections.min(openList);
			openList.remove(current);
			closedList.add(current);
			
			//Take all node around the current node
			for (int i = (int) (current.getCoor().getX() - 1); i <= current
					.getCoor().getX() + 1; i++) {
				for (int j = (int) (current.getCoor().getY() - 1); j <= current
						.getCoor().getY() + 1; j++) {
					if (i != current.getCoor().getX()
							|| j != current.getCoor().getY()) {
						Point temp = new Point(i, j);
						Node maybe = new Node(temp, current,
								(float) temp.distance(current.getCoor())
										+ current.getTotal(),
								(float) temp.distance(end));
						if (!closedList.contains(maybe)
								&& floor.getTiles()[i][j] != World.TILE.WALL
								&& floor.getTiles()[i][j] != World.TILE.ROCK) {
							openList.add(maybe);
						}
					}
				}
			}
			searchLimit--;
		}

		//System.out.println("END");
		return current;
	}

}
