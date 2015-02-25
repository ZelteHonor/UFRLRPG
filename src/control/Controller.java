package control;

import gameObjects.GameObjects;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.sun.media.jfxmediaimpl.platform.Platform;

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

	public void initGame() {

	}

	private void launchUpdater() {
		Thread th = new Thread(update);
		th.setDaemon(true);
		th.start();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TEST GRAPHIQUES ICI

		Generator gen = new Generator();

		Render render = new Render(gen.generate());
		render.drawWorld(1000, 1000);
		pane.getChildren().add(render.getGUI());

		pane.setOnKeyPressed(new EventHandler<KeyEvent>() {

			public void handle(KeyEvent ke) {

			}
		});

		update = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (GameObjects o : objects) {
					o.update();
				}
				return null;
			}
		};

		timer = new Service<Void>() {
			@Override
			protected Task<Void> createTask() {
				return new Task<Void>() {
					@Override
					protected Void call() throws InterruptedException {

						javafx.application.Platform.runLater(new Runnable() {

							@Override
							public void run() {
								launchUpdater();
							}

						});

						return null;
					}
				};
			}
		};

		// =========================================
	}

}
