package gameobject;

import world.Floor;

public class Item extends GameObject{
	private int weight;
	private boolean evil;
	
	public Item(double x, double y, int weight, boolean evil)
	{
		super(x, y);
		this.weight = weight;
		this.evil = evil;
	}

	@Override
	public void update(Floor floor) {
		angle++;
		
	}

}
