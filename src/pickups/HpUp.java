package pickups;

import control.Audio;
import world.Floor;

public class HpUp extends PickUps {

	/**
	 * La quantit�e de point de vie regagn�e
	 */
	private final int HEALTH_GAIN = 15;

	/**
	 * Constructeur
	 * 
	 * @param position
	 *            x
	 * @param position
	 *            y
	 */
	public HpUp(double x, double y) {
		super(x, y);
		sprite = "img/HpUp.png";
	}

	@Override
	/**Ajoute la vie au joueur
	 * 
	 * @param Floor
	 */
	protected void activateEffect(Floor floor) {
		floor.getPlayer()
				.setHealth(floor.getPlayer().getHealth() + HEALTH_GAIN);
		Audio.play("HpUp.wav");
	}

}
