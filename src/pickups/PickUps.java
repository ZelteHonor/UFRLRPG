package pickups;

import world.Floor;
import gameobject.Item;
import gameobject.Mask;

public abstract class PickUps extends Item{

	public PickUps(double x, double y) {
		super(x, y);
		mask = new Mask(0.10, x, y);
	}
	
	public void update(Floor floor)
	{
		if((int)x == (int)floor.getPlayer().getX() && (int)y == (int)floor.getPlayer().getY()) {
			activateEffect(floor);
			destroy = true;
		}
		
	}
	
	protected abstract void activateEffect(Floor floor);

}
