package pickups;

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
	}
	
	
	

}
