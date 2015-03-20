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

	public enum KEYSTATE {
		PRESSED, RELEASED, DOWN, UP
	};

	@FXML
	public void quit() {
		System.exit(0);
	}
	
	public void statChooser(MouseEvent e)
	{
		Button b = (Button) e.getSource();
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

		player = new Player(10, 10, 10, 10, 10, 10, 10, 10, null);
		player.setX(64);
		player.setY(64);
		player.setSprite("img/dirt.png");
		player.setAngle(90);

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
				if(mou.getButton() == MouseButton.PRIMARY)
				{
					System.out.println("left click");
					player.setKeyState(4, KEYSTATE.DOWN);
					System.out.println(player.getX());
				}
				else if(mou.getButton() == MouseButton.SECONDARY)
				{
					System.out.println("right click");
					player.setKeyState(5, KEYSTATE.DOWN);
				}
				
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
	private class GameTimer extends Service<Void>  {
		boolean t = true;
		
		public GameTimer() {}
		

		@Override
		protected Task<Void> createTask() {
			
			return new Task<Void>() {
                protected Void call() throws Exception {
                	
                    while(t){
                    	
                		Platform.runLater(
                				()->{
                					
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
	private class GameTask extends Service<Void>  {
		

		@Override
		protected Task<Void> createTask() {
			
			return new Task<Void>() {
                protected Void call() throws Exception {
                   	
                	Platform.runLater(
                			()->{
                					try
                					{
                						player.update(world.getFloor(1));
                						for (GameObjects o : objects) {
                							o.update(world.getFloor(1)); 
                						}
                					}catch(NullPointerException e){}
        						});
                		

                    return null;
                }
            };
		}
	}
	
	private class GameRender extends Service<Void>  {
		

		@Override
		protected Task<Void> createTask() {
			
			return new Task<Void>() {
                protected Void call() throws Exception {
                   	
                	Platform.runLater(
                			()->{
                					try
                					{
                						render.drawWorld(player.getX(), player.getY());
                						render.draw(player);
                					}catch(NullPointerException e){}
        						});
                		

                    return null;
                }
            };
		}
	}
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		world = new World();
	}

}
