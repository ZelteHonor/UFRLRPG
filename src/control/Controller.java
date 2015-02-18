package control;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class Controller implements Initializable {

	@FXML
	private BorderPane root;
	@FXML
	private Pane pane;

	Input input;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Input input = new Input();

		// faire en sorte qu'un mappage des touches soit fait selon un fichiers
		// externe de ssauvegarde ou de donnée storé dans des constances.
		
		

	}

}
