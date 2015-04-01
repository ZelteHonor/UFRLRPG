package entity;

import java.awt.Point;
import java.util.ArrayList;

import world.Floor;
import world.World;
import gameObjects.GameObjects;
import gameObjects.Items;

public class Monster extends Entity{
	
	private String name;
	private boolean searching;
	private Point lastTarget;

	public Monster(double x, double y, int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(x, y, level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		searching = false;
		lastTarget = new Point(-1, -1);
	}

	@Override
	public void update(Floor floor) {
		if(seePlayer(floor)){
			searching = true;
			for(GameObjects o : floor.getObjects()){
				if (o instanceof Player){
					lastTarget = new Point((int)o.getY()/64,(int)o.getY()/64);
				}
			}
		}
		else if(this.searching){
			moveTo();
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean seePlayer(Floor floor) {
		double angle = getMonsPLayAngle();
		Point pl = null;
		boolean visible = true;
		for(GameObjects o : floor.getObjects()){
			if (o instanceof Player){
				pl = new Point((int)o.getY()/64,(int)o.getY()/64);
			}
		}
		double distance = pl.distance(this.x,this.y);
		double nX;
		double nY;
		for(double d = 0; d < distance; d += distance / 100f){
			nX = d * Math.sin(angle) + this.x;
			nY = d * Math.cos(angle) + this.y;
			if(floor.getTiles()[(int)nX][(int)nY] == World.TILE.WALL 
					|| floor.getTiles()[(int)nX][(int)nY] ==  World.TILE.ROCK){
				visible = false;
			}
		}
		return visible;
	}

	private void moveTo(){
		double angle = getMonsPLayAngle();
		x += this.agility * Math.sin(angle);
		x += this.agility * Math.cos(angle);
	}
	
	private double getMonsPLayAngle(){
		return Math.atan2(lastTarget.getX() - this.x, lastTarget.getY() - this.y);
	}

}
