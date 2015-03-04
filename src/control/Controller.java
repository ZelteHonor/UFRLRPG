package control;

import entity.Player;
import gameObjects.GameObjects;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import render.Render;
import world.Generator;
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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Controller implements Initializable {
	@FXML
	private BorderPane root;
	@FXML
	private Pane pane;
	@FXML
	private Pane gamePane;

	private Task<Void> update;

	private Service<Void> timer;

	private ArrayList<GameObjects> objects;

	public enum KEYSTATE {
		PRESSED, RELEASED, DOWN, UP
	};
	@FXML
	public void quit(){
		System.exit(0);
	}
	
	/**
	 * Créer une scène contenant un bouton lancant le initGame()
	 * @throws IOException
	 */
	public void gameReady()throws IOException{
		Stage stage;
		Parent newRoot;
		stage=(Stage) pane.getScene().getWindow();
		newRoot = FXMLLoader.load(getClass().getResource("Game.fxml"));
		Scene scene = new Scene(newRoot);
	    stage.setScene(scene);
	    stage.show();
	}
	
	@FXML
	public void initGame()  {

		
		// TEST GRAPHIQUES ICI
				
		
			    
				GameObjects tryIt = new Player(10, 10, 10, 10, 10, 10, 10, 10, null);
				tryIt.setX(100);
				tryIt.setY(100);
				tryIt.setSprite("img/dirt.png");
				tryIt.setAngle(90);

				Generator gen = new Generator();

				Render render = new Render(gen.generate());
				render.drawWorld(1000, 1000);
				render.draw(tryIt);
				gamePane.getChildren().add(render.getGUI());

				gamePane.setOnKeyPressed(new EventHandler<KeyEvent>() {

					public void handle(KeyEvent ke) {
						if(ke.getCode() == KeyCode.W)
						{
							System.out.println("wow");
						}
						
					}
				});
				
				this.update = new Task<Void>() {
					@Override
					protected Void call() throws Exception {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								for(GameObjects o : objects)
								{
									o.update();
								}
							}
						});
						return null;

					};
				};

				timer = new Service<Void>() {
					@Override
					protected Task<Void> createTask() {
						
						Platform.runLater(new Runnable() {
							
							@Override
							public void run() {
								for (GameObjects o : objects) {
									o.update();
								}
							}
						});
						try {
							Thread.sleep(17);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return null;

					}

				};
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		
		
	}

}
