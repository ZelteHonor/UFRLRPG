package control;

import entity.Player;
import gameObjects.GameObjects;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import render.Render;
import world.World;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Controller implements Initializable {
	
	private static Controller controller;
	
	@FXML
	private BorderPane root;
	@FXML
	private Pane pane;

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
	
	public enum KEYSTATE {
		PRESSED, RELEASED, DOWN, UP
	};

	@FXML
	public void quit() {
		System.exit(0);
	}

	@FXML
	public void start() {
		world = new World();
		pane.getChildren().clear();
		pane.setFocusTraversable(true);
		
		player = new Player(world.getFloor().getStartX(), world.getFloor().getStartY(),1, 10, 0,0,0, 10, 10, 10, null);
		player.setSprite("img/gabriel.png");
		player.setAngle(0);
		
		objects = new ArrayList<GameObjects>();;

		world.getFloor(0).setPlayer(player);
		
		render = new Render(world.getFloor(0).getTiles());

		pane.getChildren().add(render.getGUI());

		input = new Input(pane);

		this.update = new GameTask();

		this.screenRefresh = new GameRender();

		timer = new GameTimer();
		timer.start();

	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public Render getRender()
	{
		return render;
	}

	/**
	 * gère le temps de rafraichisement du jeu appelant à chaque 60ème de
	 * secondes la tâche gameTask
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

							update.cancel();
							update.reset();
							update.start();

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
	 * appelle la méthode update sur chaque élément de la liste des éléments du
	 * jeu
	 *
	 */
	private class GameTask extends Service<Void> {

		@Override
		protected Task<Void> createTask() {

			return new Task<Void>() {
				protected Void call() throws Exception {

					Platform.runLater(() -> {

							player.update(world.getFloor(0));
							
							for (GameObjects o : objects) {

								o.update(world.getFloor(0));

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
						try {
							double cx = input.getMouse().getSceneX()
									- (render.getGUI().getWidth() / 2);
							double cy = input.getMouse().getSceneY()
									- (render.getGUI().getHeight() / 2);
							render.drawWorld(player.getX() + cx, player.getY()
									+ cy);
							render.draw(player);
						} catch (NullPointerException e) {
						}
					});
					return null;
				}
			};
		}
	}
	
	public static Controller get() {
		return controller;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		controller = this;
	}

}
