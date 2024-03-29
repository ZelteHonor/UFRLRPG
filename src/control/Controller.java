package control;

import entity.Player;
import gameobject.GameObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import world.MonsterGenerator;
import world.World;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

/**
 * Controlleur principal du jeux. Contiens la boucle du jeux.
 *
 */
public class Controller implements Initializable {

	/* Singleton-like */
	private static Controller controller;

	/**
	 * Permet au reste du programme d'acceder au controlleur.
	 * 
	 * @return Retourne le controleur
	 */
	public static Controller get() {
		return controller;
	}

	/* FXML */
	/**
	 * Racine de la fenêtre JavaFX
	 */
	@FXML
	private BorderPane root;

	/**
	 * Racine une fois le jeux lancé.
	 */
	@FXML
	private Pane pane;

	/* Modules */
	/**
	 * Instance de la classe de rendu.
	 */
	private Render render;
	/**
	 * Instance de la classe d'input.
	 */
	private Input input;
	/**
	 * Instance du monde
	 */
	private World world;

	/* Services */
	/**
	 * Instance du Timer (Voir la classe Interne Timer)
	 */
	private Service<Void> timer;
	/**
	 * Instance du Updater. (Voir la Classe Interne Updater)
	 */
	private Service<Void> updater;
	/**
	 * Instance du Renderer. (Voir la classe Interne Renderer)
	 */
	private Service<Void> refresher;

	/* Objects */
	/**
	 * Instance du Joueur
	 */
	private Player player;
	/**
	 * ArrayList qui contiens tous les objets en jeux.
	 */
	private ArrayList<GameObject> objects;

	/**
	 * ArrayList temporaire pour ajouter des objets � "objects" pendant que la
	 * boucle du jeu la parcours (et ainsi emp�cher les ConcurrentException)
	 */
	private ArrayList<GameObject> objectsToLoad;

	/**
	 * Instance du G�n�rateur de monstre.
	 */
	private MonsterGenerator m;

	/* Camera */
	/**
	 * Centre de la camera en (x,y) pour les transition lente
	 */
	private double cxto, cyto;

	/**
	 * centre de la camera (x,y)
	 */
	private double cx, cy;

	/* FXML */
	/**
	 * Initialise le programme
	 */
	public void initialize(URL location, ResourceBundle resources) {
		controller = this;
		input = new Input(pane);
		Audio.load();
	}

	@FXML
	public void start() {
		initGame();
	}

	/**
	 * Initialise le jeu
	 */
	public void initGame() {

		/* Modules */
		world = new World();
		render = new Render(world.getFloor().getTiles());

		/* FXML */
		pane.getChildren().clear();
		pane.setFocusTraversable(true);
		pane.getChildren().add(render.getGUI());
		pane.setCursor(new ImageCursor(Controller.get().getRender()
				.getSprite("img/cursor.png")));

		/* Objects */
		player = new Player(world.getFloor().getStartX(), world.getFloor()
				.getStartY());
		world.getFloor().setPlayer(player);
		objects = world.getFloor().getObjects();
		objectsToLoad = new ArrayList<GameObject>();

		/* Camera */
		cxto = player.getX();
		cyto = player.getY();
		cx = cxto;
		cy = cyto;

		/* Services */
		if (timer == null) {
			timer = new Timer();
			updater = new Updater();
			refresher = new Renderer();

			timer.start();
		}
	}

	@FXML
	public void quit() {
		System.exit(0);
	}

	/**
	 * Gère le temps de rafraichisement du jeu appelant à chaque 60ème de
	 * secondes la tâche Updater
	 * 
	 * @return null
	 */
	private class Timer extends Service<Void> {
		boolean run = true;

		public Timer() {
		}

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					while (run) {
						Platform.runLater(() -> {
							if (!updater.isRunning()) {
								updater.cancel();
								updater.reset();
								updater.start();
							}

							if (!refresher.isRunning()) {
								refresher.cancel();
								refresher.reset();
								refresher.start();
							}
						});
						try {
							Thread.sleep(17);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					return null;
				}
			};
		}
	}

	/**
	 * Appelle la méthode update sur chaque GameObject de objects<>
	 */
	private class Updater extends Service<Void> {
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						player.update(world.getFloor());
						for (GameObject o : objects)
							o.update(world.getFloor());

						/* Supressions */
						for (int i = 0; i < objects.size(); i++)
							if (objects.get(i).isDestroy())
								objects.remove(i);

						if (objectsToLoad != null)
							objects.addAll(objectsToLoad);
						objectsToLoad.clear();
					});
					return null;
				}
			};
		}
	}

	/**
	 * S'occupe de rafraichir l'écran.
	 */
	private class Renderer extends Service<Void> {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Platform.runLater(() -> {

						// Camera
						cxto = player.getX()
								+ (input.getMouse().getSceneX()
										/ render.RESOLUTION - render.DW / 2);
						cyto = player.getY()
								+ (input.getMouse().getSceneY()
										/ render.RESOLUTION - render.DH / 2);

						if (Math.sqrt(Math.pow(cxto - cx, 2)
								+ Math.pow(cyto - cy, 2)) < 1) {
							cx = cxto;
							cy = cyto;
						} else {
							double angle = Math.atan2(cyto - cy, cxto - cx);
							cx += Math.cos(angle);
							cy += Math.sin(angle);
						}

						player.setAngle(Math.atan2(cy - player.getY(), cx
								- player.getX()));

						render.drawWorld(cx, cy);
						render.draw(player.getWeapon());
						render.draw(player);
						render.draw(objects);
						render.drawHUD();
					});

					return null;
				}
			};
		}
	}

	/* Objects */
	/**
	 * @return renvoie l'instance du joueur
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * 
	 * @return renvoir tout les GameObjets pr�sent dans la partie
	 */
	public ArrayList<GameObject> getObjects() {
		return objects;
	}

	/**
	 * 
	 * @param red
	 *            �finie le contenue de objects
	 */
	public void setObjects(ArrayList<GameObject> array) {
		objects = array;
	}

	/**
	 * 
	 * @return l'int�gralit� de tout les floors de la partie
	 */
	public World getWorld() {
		return world;
	}

	/* Modules */

	/**
	 * 
	 * @return renvoi le render qui affiche tout ce qui est visible
	 */
	public Render getRender() {
		return render;
	}

	/**
	 * 
	 * @return renvoi l'objet contenant les entr�s des touches et de la sourie
	 */
	public Input getInput() {
		return input;
	}

	/**
	 * 
	 * @return renvoi le pane
	 */
	public Pane getPane() {
		return pane;
	}

	/* Camera */
	/**
	 * 
	 * @return renvoi le centre de la camera en x
	 */
	public double getCX() {
		return cx;
	}

	/**
	 * 
	 * @return renvoi le centre de la camera en y
	 */
	public double getCY() {
		return cy;
	}

	/**
	 * 
	 * @return renvoi la liste temporaire de GameObjects � ajouter � la liste
	 *         principale (objects)
	 */
	public ArrayList<GameObject> getObjectsToLoad() {
		return objectsToLoad;
	}
}
