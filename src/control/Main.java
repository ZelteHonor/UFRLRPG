package control;

import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {
	
	public static void main(String args[]) {
		System.out.println("Java stinks");
		launch();
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("app.fxml"));
			Scene scene = new Scene(root);
			
			stage.setTitle("UFRLRPG : Unoriginal till the end!");
			stage.setWidth(1280);
			stage.setHeight(720);
			stage.setResizable(false);
			
			stage.setScene(scene);
			stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
