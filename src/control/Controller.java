package control;

import gameObjects.GameObjects;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import render.Render;
import world.Generator;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

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
	
	public void initGame()
	{
		
	}
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		//TEST GRAPHIQUES ICI
		
		GameObjects tryIt = new GameObjects();
		tryIt.setX(100);
		tryIt.setY(100);
		tryIt.setSprite("img/dirt.png");
		tryIt.setAngle(90);
		
		Generator gen = new Generator();

		Render render = new Render(gen.generate());
		render.drawWorld(1000, 1000);
		render.draw(tryIt);
		pane.getChildren().add(render.getGUI());
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
            public void handle(KeyEvent ke) {
                
            }
		});
		//=========================================
	}
}
