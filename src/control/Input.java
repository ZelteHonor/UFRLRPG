package control;

import control.Controller.KEYSTATE;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Input {
	
	private MouseEvent mouse;
	private double relativeMousePX;
	private double relativeMousePY;
	
	public Input(Node node) {
		
		node.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {
					System.out.println("W down");

					Controller.get().getPlayer().setKeyState(0, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.A) {
					System.out.println("A down");

					Controller.get().getPlayer().setKeyState(2, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.S) {
					System.out.println("S down");

					Controller.get().getPlayer().setKeyState(1, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.D) {
					System.out.println("D down");

					Controller.get().getPlayer().setKeyState(3, KEYSTATE.DOWN);
				}
			}
		});

		node.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {
					System.out.println("W down");

					Controller.get().getPlayer().setKeyState(0, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.A) {
					System.out.println("A down");

					Controller.get().getPlayer().setKeyState(2, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.S) {
					System.out.println("S down");

					Controller.get().getPlayer().setKeyState(1, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.D) {
					System.out.println("D down");

					Controller.get().getPlayer().setKeyState(3, KEYSTATE.RELEASED);
				}
			}
		});

		node.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {
				if (mou.getButton() == MouseButton.PRIMARY) {
					System.out.println("left click");
					Controller.get().getPlayer().setKeyState(4, KEYSTATE.DOWN);
					System.out.println(Controller.get().getPlayer().getX());
				} else if (mou.getButton() == MouseButton.SECONDARY) {
					System.out.println("right click");
					Controller.get().getPlayer().setKeyState(5, KEYSTATE.DOWN);
				}

			}

		});
		node.setOnMouseMoved(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {

				mouse = mou;
				relativeMousePX = (Controller.get().getRender().getDW() * Controller.get().getRender().getRESOLUTION() / 2)
						- mou.getSceneX();
				relativeMousePY = (Controller.get().getRender().getDH() * Controller.get().getRender().getRESOLUTION() / 2)
						- mou.getSceneY();

				// Mettre plus tard dans joueur.update()
				Controller.get().getPlayer().setAngle(Math.toDegrees(Math.atan2(relativeMousePY,
						relativeMousePX)) + 180);// ====================================================================La
													// classe joueur à le défaut
													// de ne pas voir ce qu'il
													// faut dans le
													// contrôleur!(À CHANGER)
			}

		});

		node.setOnMouseDragged(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mou) {

				Controller.get();
				mouse = mou;
				relativeMousePX = (Controller.get().getRender().getDW() * Controller.get().getRender().getRESOLUTION() / 2)
						- mou.getSceneX();
				relativeMousePY = (Controller.get().getRender().getDH() * Controller.get().getRender().getRESOLUTION() / 2)
						- mou.getSceneY();

				// Mettre plus tard dans joueur.update()
				Controller.get().getPlayer().setAngle(Math.toDegrees(Math.atan2(relativeMousePY,
						relativeMousePX)) + 180);// ====================================================================La
													// classe joueur à le défaut
													// de ne pas voir ce qu'il
													// faut dans le
													// contrôleur!(À CHANGER)
			}

		});

		
	}

	public MouseEvent getMouse() {
		return mouse;
	}


	public double getRelativeMousePX() {
		return relativeMousePX;
	}

	public double getRelativeMousePY() {
		return relativeMousePY;
	}
	
}