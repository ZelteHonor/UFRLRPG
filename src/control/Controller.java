package control;

import entity.Monster;
import entity.Player;
import gameobject.GameObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import world.Floor;
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

	/* Singleton-like */
	private static Controller controller;
	public static Controller get() {
		return controller;
	}

	/* FXML */
	@FXML private BorderPane root;
	@FXML private Pane pane;
	
	/* Modules */
	private Render render;
	private Input input;
	private World world;

	/* Services */
	private Service<Void> timer;
	private Service<Void> updater;
	private Service<Void> refresher;

	/* Objects */
	private Player player;
	private ArrayList<GameObject> objects;

	private MonsterGenerator m;
	
	/* Camera */
	private double cx, cy;
	
	
	/* FXML */
	public void initialize(URL location, ResourceBundle resources) {
		controller = this;
		input = new Input(pane);
	}
	
	@FXML
	public void start() {
		initGame();
	}

	public void initGame() {

		/* Modules */
		world = new World();
		render = new Render(world.getFloor().getTiles());

		/* FXML */
		pane.getChildren().clear();
		pane.setFocusTraversable(true);
		pane.getChildren().add(render.getGUI());

		/* Objects */
		player = new Player(world.getFloor().getStartX(), world.getFloor().getStartY());
		world.getFloor().setPlayer(player);
		
		objects = world.getFloor().getObjects();

		/* Services */
		timer = new Timer();
		updater = new Updater();
		refresher = new Renderer();
		
		timer.start();
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
	private  class Timer extends Service<Void> {
		boolean run = true;

		public Timer() {
		}

		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					while (run) {
						Platform.runLater(() -> {
							if(!updater.isRunning()) {
								updater.cancel();
								updater.reset();
								updater.start();
							}

							if(!refresher.isRunning()) {
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
						
						for(int i = 0; i < objects.size(); i++)
							if(objects.get(i).isDestroy())
								objects.remove(i);
					}

					);
					return null;
				}
			};
		}
	}

	private class Renderer extends Service<Void> {
		@Override
		protected Task<Void> createTask() {
			return new Task<Void>() {
				protected Void call() throws Exception {
					Platform.runLater(() -> {

						// Camera
						double cx = player.getX() + (input.getMouse().getSceneX() / render.RESOLUTION - render.DW / 2);
						double cy = player.getY() + (input.getMouse().getSceneY()/ render.RESOLUTION - render.DH / 2);
						
						player.setAngle(Math.atan2(cy-player.getY(), cx-player.getX()));
						
						render.drawWorld(cx, cy);
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
	public Player getPlayer() {
		return player;
	}

	public void setObjects(ArrayList<GameObject> array) {
		objects = array;
	}

	/* World */
	public World getWorld() {
		return world;
	}
	
	/* Modules */
	public Render getRender() {
		return render;
	}
	public Input getInput() {
		return input;
	}

	/* Camera */
	public double getCX() {
		return cx;
	}
	public double getCY() {
		return cy;
	}
}
