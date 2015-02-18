package control;


/**
 * 
 * @author djanelle
 *
 */
public class Input {

	public enum KEYSTATE {
		UP, DOWN, PRESSED, RELEASED
	};

	private KEYSTATE[] touches;
	private int mx, my;

	public Input() {
		touches = new KEYSTATE[5];
		this.mx = 0;
		this.my = 0;
	}
	
	

}
