package control;

import java.net.URL;
import java.util.ResourceBundle;

import render.Render;
import world.Generator;
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
	
	
	
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		
		//TEST GRAPHIQUES ICI
		
		Generator gen = new Generator();

		Render render = new Render(gen.generate());
		render.drawWorld(1000, 1000);
		pane.getChildren().add(render.getGUI());
		
		pane.setOnKeyPressed(new EventHandler<KeyEvent>(){
			
            public void handle(KeyEvent ke) {
                
            }
		});
		//=========================================
	}
}
