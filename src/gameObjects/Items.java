package gameObjects;

public class Items extends GameObjects{
	private int weight;
	private boolean evil;
	
	public Items(int weight, boolean evil)
	{
		super();
		this.weight = weight;
		this.evil = evil;
	}

	@Override
	public void update() {
		angle++;
		
	}

}
