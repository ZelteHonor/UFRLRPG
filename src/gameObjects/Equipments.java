package gameObjects;

public class Equipments extends Items{

	private int protection;
	private int attBonus;
	private String att;
	
	
	public Equipments(double x, double y, int weight, boolean evil, int protection, int attBonus, String att) {
		super(x, y, weight, evil);
		this.protection = protection;
		this.attBonus = attBonus;
		this.att = att;
		
		
	}

}
