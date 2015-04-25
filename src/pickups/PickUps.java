package pickups;

import world.Floor;
import gameobject.Item;

public abstract class PickUps extends Item{

	public PickUps(double x, double y) {
		super(x, y);
	}
	
	public void update(Floor floor)
	{
		if(x == floor.getPlayer().getX() && y == floor.getPlayer().getY())
			activateEffect(floor);
	
		
	}
	
	protected abstract void activateEffect(Floor floor);

}
