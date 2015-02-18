package gameObjects;

public class Equipments extends Items{

	private int protection;
	private int attBonus;
	private String att;
	
	
	public Equipments(int weight, boolean evil, int protection, int attBonus, String att) {
		super(weight, evil);
		this.protection = protection;
		this.attBonus = attBonus;
		this.att = att;
		
		
	}

}
