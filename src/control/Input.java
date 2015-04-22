package control;

import control.Controller.KEYSTATE;
import world.MonsterGenerator;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Initialise tout les écouteurs et permet d'acceder à la souris.
 *
 */
public class Input {
	
	private MouseEvent mouse;
	private double mx;
	private double my;
	
	
	
	/** Constructeur */
	public Input(Node node) {
		
		//PRESSED
		node.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {
					Controller.get().getPlayer().setKeyState(0, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.A) {
					Controller.get().getPlayer().setKeyState(2, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.S) {
					Controller.get().getPlayer().setKeyState(1, KEYSTATE.DOWN);
				} else if (ke.getCode() == KeyCode.D) {
					Controller.get().getPlayer().setKeyState(3, KEYSTATE.DOWN);
				}
			}
		});

		//RELEASSED
		node.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				if (ke.getCode() == KeyCode.W) {

					Controller.get().getPlayer().setKeyState(0, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.A) {
					Controller.get().getPlayer().setKeyState(2, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.S) {

					Controller.get().getPlayer().setKeyState(1, KEYSTATE.RELEASED);
				} else if (ke.getCode() == KeyCode.D) {

					Controller.get().getPlayer().setKeyState(3, KEYSTATE.RELEASED);
				}
			}
		});

		//MOUSE PRESSED
		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mou) {
				if (mou.getButton() == MouseButton.PRIMARY) {
					Controller.get().getPlayer().setKeyState(4, KEYSTATE.PRESSED);
				} else if (mou.getButton() == MouseButton.SECONDARY) {
					Controller.get().getPlayer().setKeyState(5, KEYSTATE.PRESSED);
				}

			}
			
			

		});
		
		//MOUSE RELEASED
				node.setOnMouseReleased(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent mou) {
						if (mou.getButton() == MouseButton.PRIMARY) {
							System.out.println("left click");
							Controller.get().getPlayer().setKeyState(4, KEYSTATE.UP);
							System.out.println(Controller.get().getPlayer().getX());
						} else if (mou.getButton() == MouseButton.SECONDARY) {
							System.out.println("right click");
							Controller.get().getPlayer().setKeyState(5, KEYSTATE.UP);
						}

					}
					
					

				});
		
		
		

		//MOUSE PRESSED
		node.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mou) {
				if (mou.getButton() == MouseButton.PRIMARY) {
					Controller.get().getPlayer().setKeyState(4, KEYSTATE.RELEASED);
				} else if (mou.getButton() == MouseButton.SECONDARY) {
					Controller.get().getPlayer().setKeyState(5, KEYSTATE.RELEASED);
				}

			}

		});
		
		//MOUSSE MOVED
		node.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent mou) {
				mouse = mou;
			}

		});	
		
		//MOUSSE MOVED
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent mou) {
						mouse = mou;
						mx = (Controller.get().getRender().getDW() * Controller.get().getRender().getRESOLUTION() / 2)
								- mou.getSceneX();
						my = (Controller.get().getRender().getDH() * Controller.get().getRender().getRESOLUTION() / 2)
								- mou.getSceneY();

						// Mettre plus tard dans joueur.update()
						Controller.get().getPlayer().setAngle(Math.toDegrees(Math.atan2(my,mx)));
						// ====================================================================La
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
		return mx;
	}

	public double getRelativeMousePY() {
		return my;
	}
	
}
