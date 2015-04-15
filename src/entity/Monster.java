package entity;

import java.awt.Point;
import java.util.ArrayList;

import control.Controller;
import pathfinding.Node;
import world.Floor;
import world.World;
import gameObjects.GameObjects;
import gameObjects.Items;

public class Monster extends Entity{
	
	private String name;
	private boolean searching;
	private Point lastTarget;
	private Node totalPath, nextNode;
	
	public Monster(double x, double y, int level, int health, int intellect, int strenght,
			int agility, int mana, int speed, int perception, ArrayList<Items> inventory) {
		super(x, y, level, health, intellect, strenght, agility, mana, speed, perception, inventory);
		searching = false;
		lastTarget = new Point(-1, -1);
		totalPath = null;
	}

	@Override
	public void update(Floor floor) {
		if(seePlayer(floor)){
			searching = true;
			lastTarget = new Point((int)Controller.get().getPlayer().getX(),(int)Controller.get().getPlayer().getY());
			totalPath = pathfinding.Pathfinding.getPath(new Point((int)this.x,(int)this.y), lastTarget, floor);
			nextNode = totalPath.getFirst();
			angle = Math.atan2(x - Controller.get().getPlayer().getX(), y - Controller.get().getPlayer().getY());
			moveTo();
		}
		else if(this.searching){
			if(new Point((int)this.x,(int)this.y).distance(lastTarget) <= 1.5){
				System.out.println("platghypus");
				searching = false;
			}
			else if(new Point((int)this.x,(int)this.y).equals(nextNode.getCoor())){
				nextNode = totalPath.getDestructiveFirst();
			}
			else{
				moveTo();
			}
			
			
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private boolean seePlayer(Floor floor) {
		Point pl = null;
		boolean visible = false;

		pl = new Point((int)floor.getPlayer().getX(),(int)floor.getPlayer().getY());

		double distance = pl.distance(this.x,this.y);
		if (distance <= 5){
			visible = true;
		}
		return visible;
	}

	private void moveTo(){
		double angle = getMonsTargetAngle();
		x += 0.1 * Math.sin(angle);
		y += 0.1 * Math.cos(angle);
	}
	
	private double getMonsTargetAngle(){
		return Math.atan2(nextNode.getCoor().getX() - this.x, nextNode.getCoor().getY() - this.y);
	}

}
