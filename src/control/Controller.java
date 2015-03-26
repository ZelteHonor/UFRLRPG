package control;

import entity.Player;
import gameObjects.GameObjects;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import render.Render;
import world.Generator;
import world.World;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private BorderPane mainRoot;
	@FXML
	private BorderPane gameRoot;
	@FXML
	private BorderPane charRoot;

	@FXML
	private Pane gamePane;

	private static String gameState;

	@FXML
	private Label totalP;
	@FXML
	private Label strengthP;
	@FXML
	private Label intellectP;
	@FXML
	private Label agilityP;

	@FXML
	private Button strM;
	@FXML
	private Button strL;
	@FXML
	private Button intM;
	@FXML
	private Button intL;
	@FXML
	private Button aglM;
	@FXML
	private Button aglL;

	private World world;

	private Service<Void> update;

	private Service<Void> screenRefresh;

	private Service<Void> timer;

	private ArrayList<GameObjects> objects;

	private Player player;

	private Render render;

	private MouseEvent mouse;
	private double relativeMousePX;
	private double relativeMousePY;

	private static SimpleStringProperty intP, strP, aglP, remP;

	public enum KEYSTATE {
		PRESSED, RELEASED, DOWN, UP
	};

	@FXML
	public void quit() {
		System.exit(0);
	}

	@FXML
	public void statChooser(MouseEvent e) {
		Button b = (Button) e.getSource();
		if (b == strM && Integer.parseInt(remP.get()) > 0) {
			strP.set(Integer.toString(Integer.parseInt(strP.get()) + 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) - 1));
		} else if (b == strL && Integer.parseInt(strP.get()) > 0) {
			strP.set(Integer.toString(Integer.parseInt(strP.get()) - 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) + 1));
		} else if (b == intM && Integer.parseInt(remP.get()) > 0) {
			intP.set(Integer.toString(Integer.parseInt(intP.get()) + 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) - 1));
		} else if (b == intL && Integer.parseInt(intP.get()) > 0) {
			intP.set(Integer.toString(Integer.parseInt(intP.get()) - 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) + 1));
		} else if (b == aglM && Integer.parseInt(remP.get()) > 0) {
			aglP.set(Integer.toString(Integer.parseInt(aglP.get()) + 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) - 1));
		} else if (b == aglL && Integer.parseInt(aglP.get()) > 0) {
			aglP.set(Integer.toString(Integer.parseInt(aglP.get()) - 1));
			remP.set(Integer.toString(Integer.parseInt(remP.get()) + 1));
		}
	}

	/**
	 * Créer une scène contenant un bouton lancant le initGame()
	 * 
	 * @throws IOException
	 */
	public void gameReady() throws IOException {
		Stage stage;
		stage = (Stage) charRoot.getScene().getWindow();
		gameRoot = FXMLLoader.load(getClass().getResource("Game.fxml"));
		Scene scene = new Scene(gameRoot);
		stage.setScene(scene);
		stage.show();
	}

	public void createCharacter() throws IOException {
		Stage stage;
		stage = (Stage) mainRoot.getScene().getWindow();
		charRoot = FXMLLoader.load(getClass().getResource(
				"CharacterCreation.fxml"));
		Scene scene = new Scene(charRoot);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	public void initGame() {

		player = new Player(1, 10, Integer.parseInt(intP.getValue()),
				Integer.parseInt(strP.getValue()), Integer.parseInt(aglP
						.getValue()), 10, 10, 10, null);
		player.setX(64);
		player.setY(64);
		player.setSprite("img/gabriel.png");
		player.setAngle(0);

		render = new Render(world.getFloor(0).getTiles());

		gamePane.getChildren().add(render.getGUI());

		gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {
					System.out.println("W down");

					player.setKeyState(0, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.A) {
					System.out.println("A down");

					player.setKeyState(2, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.S) {
					System.out.println("S down");

					player.setKeyState(1, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.D) {
					System.out.println("D down");

					player.setKeyState(3, KEYSTATE.DOWN);
				}
			}
		});

		gamePane.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {
					System.out.println("W down");

					player.setKeyState(0, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.A) {
					System.out.println("A down");

					player.setKeyState(2, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.S) {
					System.out.println("S down");

					player.setKeyState(1, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.D) {
					System.out.println("D down");

					player.setKeyState(3, KEYSTATE.RELEASED);
				}
			}
		});

		gamePane.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {
				if (mou.getButton() == MouseButton.PRIMARY) {
					System.out.println("left click");
					player.setKeyState(4, KEYSTATE.DOWN);
					System.out.println(player.getX());
				} else if (mou.getButton() == MouseButton.SECONDARY) {
					System.out.println("right click");
					player.setKeyState(5, KEYSTATE.DOWN);
				}

			}

		});
		gamePane.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {

				mouse = mou;
				relativeMousePX = (render.getDW() * render.getRESOLUTION() / 2)
						- mou.getSceneX();
				relativeMousePY = (render.getDH() * render.getRESOLUTION() / 2)
						- mou.getSceneY();

				// Mettre plus tard dans joueur.update()
				player.setAngle(Math.toDegrees(Math.atan2(relativeMousePY,
						relativeMousePX)) + 180);// ====================================================================La
													// classe joueur à le défaut
													// de ne pas voir ce qu'il
													// faut dans le
													// contrôleur!(À CHANGER)
			}

		});

		gamePane.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {

				mouse = mou;
				relativeMousePX = (render.getDW() * render.getRESOLUTION() / 2)
						- mou.getSceneX();
				relativeMousePY = (render.getDH() * render.getRESOLUTION() / 2)
						- mou.getSceneY();

				// Mettre plus tard dans joueur.update()
				player.setAngle(Math.toDegrees(Math.atan2(relativeMousePY,
						relativeMousePX)) + 180);// ====================================================================La
													// classe joueur à le défaut
													// de ne pas voir ce qu'il
													// faut dans le
													// contrôleur!(À CHANGER)
			}

		});

		this.update = new GameTask();

		this.screenRefresh = new GameRender();

		timer = new GameTimer();
		timer.start();

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
						try {
							player.update(world.getFloor(0));
							for (GameObjects o : objects) {
								o.update(world.getFloor(0));
							}
						} catch (NullPointerException e) {
						}
					});

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
							double cx = mouse.getSceneX()
									- (render.getGUI().getWidth() / 2);
							double cy = mouse.getSceneY()
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (location.toString().contains("Game.fxml"))
			world = new World();

		if (location.toString().contains("CharacterCreation.fxml")) {
			strP = new SimpleStringProperty();
			strP.set("5");
			strengthP.textProperty().bind(strP);
			intP = new SimpleStringProperty();
			intP.set("5");
			intellectP.textProperty().bind(intP);
			aglP = new SimpleStringProperty();
			aglP.set("5");
			agilityP.textProperty().bind(aglP);
			remP = new SimpleStringProperty();
			remP.set("30");
			totalP.textProperty().bind(remP);
		}

	}

}
