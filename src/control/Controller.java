package control;

import entity.Monster;
import entity.Player;
import gameObjects.GameObjects;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import render.Render;
import world.MonsterGenerator;
import world.World;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Controller implements Initializable {

	/* Singleton - like */
	private static Controller controller;

	/* FXML */
	@FXML
	private BorderPane root;
	@FXML
	private Pane pane;

	/* IDK */
	private static String gameState;

	/* Modules */
	private Render render;
	private Input input;
	private World world;

	/* Services */
	private Service<Void> update;
	private Service<Void> screenRefresh;
	private Service<Void> timer;

	/* Objects */
	private Player player;
	private ArrayList<GameObjects> objects;

	private MonsterGenerator m;

	/* Clavier */
	public enum KEYSTATE {
		PRESSED, RELEASED, DOWN, UP
	};

	@FXML
	public void start() {
		initGame();
	}

	public void initGame() {

		/* Modules */
		input = new Input(pane);
		world = new World();
		render = new Render(world.getFloor(0).getTiles());

		/* FXML */
		pane.getChildren().clear();
		pane.setFocusTraversable(true);
		pane.getChildren().add(render.getGUI());

		/* Objects */
		player = new Player(world.getFloor().getStartX(), world.getFloor()
				.getStartY(), 1, 10, 0, 0, 0, 10, 10, 10, null);
		player.setX(world.getFloor().getStartX());
		player.setY(world.getFloor().getStartY());
		player.setSprite("img/jaypeg.png");
		player.setAngle(0);
		world.getFloor().setPlayer(player);
		
		objects = world.getFloor().getObjects();
		
		//++++++++++++++++++++++++++++++POUR TEST
/*		player.setX(2);
		player.setY(2);
		
		Monster m = new Monster(3, 3, 1, null);
		m.setAngle(0);
		m.setSprite("img/gabriel.png");
		objects.add(m);*/
		//+++++++++++++++++++++++++++++++

		/* Services */
		this.update = new GameTask();
		this.screenRefresh = new GameRender();

		timer = new GameTimer();
		timer.start();
	}

	@FXML
	public void quit() {
		System.exit(0);
	}

	/**
	 * g�re le temps de rafraichisement du jeu appelant �chaque 60�me de
	 * secondes la t�che gameTask
	 * 
	 * @return null
	 */
	private class GameTimer extends Service<Void> {
		boolean t = true;

		public GameTimer() {
		}

		@Override
		protected Task<Void> createTask() {

			return new Task<Void>() {
				protected Void call() throws Exception {

					while (t) {

						Platform.runLater(() -> {

							if(!update.isRunning())
							{
								update.cancel();
								update.reset();
								update.start();
							}

							screenRefresh.cancel();
							screenRefresh.reset();
							screenRefresh.start();
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
	 * appelle la m�thode update sur chaque �l�ment de la liste des
	 * �l�ments du jeu
	 */
	private class GameTask extends Service<Void> {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Platform.runLater(() -> {
						player.update(world.getFloor(0));
						for (GameObjects o : objects)
							o.update(world.getFloor(0));

						if (world.reachedExit()) {
							System.out.println("I've reached the exit");
							initGame();
						}
						
					}

					);
					return null;
				}
			};
		}
	}

	private class GameRender extends Service<Void> {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Platform.runLater(() -> {

						// Camera
						double cx = player.getX()
								+ (input.getMouse().getSceneX()
										/ render.getRESOLUTION() - render
										.getDW() / 2);
						double cy = player.getY()
								+ (input.getMouse().getSceneY()
										/ render.getRESOLUTION() - render
										.getDH() / 2);

						render.drawWorld(cx, cy);
						render.draw(player);
						render.draw(objects);


					});

					return null;
				}
			};
		}
	}

	public static Controller get() {
		return controller;
	}

	public Player getPlayer() {
		return player;
	}

	public Render getRender() {
		return render;
	}

	public Input getInput() {
		return input;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = this;
	}

}
