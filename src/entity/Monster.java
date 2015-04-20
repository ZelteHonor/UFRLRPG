package entity;

import java.awt.Point;
import java.util.ArrayList;

import control.Controller;
import pathfinding.Node;
import world.Floor;
import world.World;
import world.World.TILE;
import gameObjects.GameObjects;
import gameObjects.Items;
import gameObjects.Mask;

public class Monster extends Entity{
	
	private String name;
	private boolean searching;
	private Point lastTarget;
	private Node totalPath, nextNode;
	
	public Monster(double x, double y,int health, ArrayList<Items> inventory) {
		super(x, y, health, inventory);
		searching = false;
		lastTarget = new Point(-1, -1);
		totalPath = null;
		
		mask = new Mask(0.25, 0.25,x,y);
	}

	@Override
	public void update(Floor floor) {
		if(seePlayer(floor)){		
			searching = true;
			lastTarget = new Point((int)Controller.get().getPlayer().getX(),(int)Controller.get().getPlayer().getY());
			totalPath = pathfinding.Pathfinding.getPath(new Point((int)this.x,(int)this.y), lastTarget, floor);
			nextNode = totalPath.getFirst();
			angle = Math.toDegrees(Math.atan2(y - Controller.get().getPlayer().getY(),x - Controller.get().getPlayer().getX()))+180;
			moveTo();
		}
		else if(this.searching){
			if(new Point((int)this.x,(int)this.y).distance(lastTarget) <= 1){
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
			
			
			
			double m = (Controller.get().getPlayer().getY() - y) / (Controller.get().getPlayer().getX() - x);
			double b = y - m*x;
			
			if(x <= (Controller.get().getPlayer().getX()))
				for(double i = x; i < Controller.get().getPlayer().getX(); i = i + 0.1)
				{
					System.out.println("("+i+" "+Controller.get().getPlayer().getX()+")");
					if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
						return false;
				}
			else
				for(double i = x; i > Controller.get().getPlayer().getX(); i = i - 0.1)
				{
					System.out.println("-("+i+" "+Controller.get().getPlayer().getX()+")");
					if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
						return false;
				}
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
