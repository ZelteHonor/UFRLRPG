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
		
		//Keyboard Pressed
		node.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
				case W:
					Controller.get().getPlayer().setKeyState(0, KEYSTATE.PRESSED); break;
				case S:
					Controller.get().getPlayer().setKeyState(1, KEYSTATE.PRESSED); break;
				case A:
					Controller.get().getPlayer().setKeyState(2, KEYSTATE.PRESSED); break;
				case D:
					Controller.get().getPlayer().setKeyState(3, KEYSTATE.PRESSED); break;
				case SPACE:
					Controller.get().getPlayer().setKeyState(4, KEYSTATE.PRESSED); break;
				default:
					break;
				}
			}
		});

		//Keyboard Released
		node.setOnKeyReleased(new EventHandler<KeyEvent>() {
			public void handle(KeyEvent ke) {
				switch(ke.getCode()) {
				case W:
					Controller.get().getPlayer().setKeyState(0, KEYSTATE.RELEASED); break;
				case S:
					Controller.get().getPlayer().setKeyState(1, KEYSTATE.RELEASED); break;
				case A:
					Controller.get().getPlayer().setKeyState(2, KEYSTATE.RELEASED); break;
				case D:
					Controller.get().getPlayer().setKeyState(3, KEYSTATE.RELEASED); break;
				case SPACE:
					Controller.get().getPlayer().setKeyState(4, KEYSTATE.RELEASED); break;
				default:
					break;
				}
			}
		});

		//MouseButton Pressed
		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				switch(me.getButton()) {
				case PRIMARY: 
					Controller.get().getPlayer().setKeyState(5, KEYSTATE.PRESSED); break;
				case SECONDARY: 
					Controller.get().getPlayer().setKeyState(6, KEYSTATE.PRESSED); break;
				default:
					break;
				}
			}
		});
		
		//MouseButton Released	
		node.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				switch(me.getButton()) {
				case PRIMARY: 
					Controller.get().getPlayer().setKeyState(5, KEYSTATE.RELEASED); break;
				case SECONDARY: 
					Controller.get().getPlayer().setKeyState(6, KEYSTATE.RELEASED); break;
				default:
					break;
				}
			}
		});
		
		//Mouse movement
		node.setOnMouseMoved(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mouse = me;
			}
		});	
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent me) {
				mouse = me;
			}
		});	
	}

	public MouseEvent getMouse() {
		return mouse;
	}
	
	public double getMX() {
		return Controller.get().getCX() + mouse.getSceneX();
	}
	public double getMY() {
		return Controller.get().getCY() + mouse.getSceneY();
	}
}
