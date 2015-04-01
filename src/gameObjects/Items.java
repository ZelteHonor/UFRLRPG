package gameObjects;

import world.Floor;

public class Items extends GameObjects{
	private int weight;
	private boolean evil;
	
	public Items(double x, double y, int weight, boolean evil)
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
