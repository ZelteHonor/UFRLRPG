package gameobject;

public class Armor extends Item{

	private int protection;
	private int attBonus;
	private String att;
	
	
	public Armor(double x, double y, int weight, boolean evil, int protection, int attBonus, String att) {
		super(x, y, weight, evil);
		this.protection = protection;
		this.attBonus = attBonus;
		this.att = att;
		
		
	}

}
