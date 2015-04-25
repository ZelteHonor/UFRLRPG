package pickups;

import control.Audio;
import entity.Player;
import gameobject.Mask;
import world.Floor;

public class HpUp extends PickUps {
	
	private int healthGain;

	public HpUp(double x, double y) {
		super(x, y);
		sprite = "img/HpUp.png";
		mask = new Mask(0.10, x, y);
		healthGain = 15;
	}
	
	@Override
	protected void activateEffect(Floor floor) {
		
		
		floor.getPlayer().setHealth(floor.getPlayer().getHealth() + healthGain);
		Audio.play("HpUp.wav");
		if(floor.getPlayer().getHealth() > Player.MAX_HEALTH)
		{
			floor.getPlayer().setHealth(Player.MAX_HEALTH);
		}
		
		
	}
	
	
	

}
