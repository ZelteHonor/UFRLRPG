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
		
		mask = new Mask(0.15 ,x,y);
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
			if(new Point((int)this.x,(int)this.y).distance(lastTarget) <= 0.1){
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
					if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
						return false;
				}
			else
				for(double i = x; i > Controller.get().getPlayer().getX(); i = i - 0.1)
				{
					if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
						return false;
				}
		}
		
		
		return visible;
	}

	private void moveTo(){
		double angle = getMonsTargetAngle();
		Double vx = 0.1 * Math.sin(angle);
		Double vy = 0.1 * Math.cos(angle);
		
		boolean hCheckW =  false;
		boolean vCheckW = false;
		boolean hCheckE =  false;
		boolean vCheckE = false;
		
		ArrayList<Mask> walls = Controller.get().getWorld().getFloor().getWalls();
		for(Mask wall : walls) {
			mask.setX(x+vx);
			mask.setY(y+vy);
			if (Mask.collide(mask, wall)) {
				mask.setY(y);
				if (Mask.collide(mask,wall))
					hCheckW = true;
				mask.setX(x);
				mask.setY(y+vy);
				if (Mask.collide(mask,wall))
					vCheckW = true;		
			}
		}
		
		for(GameObjects o : Controller.get().getWorld().getFloor().getObjects()) {
			if(o != this){
				mask.setX(x+vx);
				mask.setY(y+vy);
				if (Mask.collide(mask, o.getMask())) {
					mask.setY(y);
					if (Mask.collide(mask,o.getMask()))
						hCheckE = true;
					mask.setX(x);
					mask.setY(y+vy);
					if (Mask.collide(mask,o.getMask()))
						vCheckE = true;		
				}
			}
		}
		
		if (!hCheckW && !hCheckE)
			x += vx;
		if (!vCheckW && !vCheckE)
			y += vy;
	}
	
	private double getMonsTargetAngle(){
		return Math.atan2(nextNode.getCoor().getX() - this.x + 0.5, nextNode.getCoor().getY() - this.y + 0.5);
	}

}
