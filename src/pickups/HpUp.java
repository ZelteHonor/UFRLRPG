package pickups;

import control.Audio;
import world.Floor;

public class HpUp extends PickUps {
	
	private int healthGain;

	public HpUp(double x, double y) {
		super(x, y);
		sprite = "img/HpUp.png";
		healthGain = 15;
	}
	
	@Override
	protected void activateEffect(Floor floor) {
		floor.getPlayer().setHealth(floor.getPlayer().getHealth() + healthGain);
		Audio.play("HpUp.wav");
	}
	
	
	

}
