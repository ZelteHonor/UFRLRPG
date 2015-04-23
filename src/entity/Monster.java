package entity;

import java.awt.Point;
import java.util.ArrayList;

import control.Controller;
import pathfinding.Node;
import world.Floor;
import world.World;
import world.World.TILE;
import gameobject.GameObject;
import gameobject.Item;
import gameobject.Mask;

public class Monster extends Entity{
	
	private String name;
	private boolean searching;
	private Point lastTarget;
	private Node totalPath, nextNode;
	public final static float SPEED = 0.09f;
	
	public Monster(double x, double y,int health) {
		super(x, y, health);
		searching = false;
		lastTarget = new Point(-1, -1);
		totalPath = null;
		
		mask = new Mask(0.15 ,x,y);
	}

	@Override
	public void update(Floor floor) {
		if (health <= 0) {
			destroy = true;
		}
		
		if(seePlayer(floor)){		
			searching = true;
			lastTarget = new Point((int)Controller.get().getPlayer().getX(),(int)Controller.get().getPlayer().getY());
			totalPath = pathfinding.Pathfinding.getPath(new Point((int)this.x,(int)this.y), lastTarget, floor);
			nextNode = totalPath.getFirst();
			angle = Math.atan2(Controller.get().getPlayer().getY() - y,Controller.get().getPlayer().getX() - x);
			moveTo();
		}
		else if(this.searching){
			//System.out.println("ds");
			
			if(new Point((int)this.x,(int)this.y).distance(lastTarget) <= 0.1){
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
		Point player = new Point((int)floor.getPlayer().getX(),(int)floor.getPlayer().getY());

		if (Math.sqrt(Math.pow(floor.getPlayer().getX() - x, 2) + Math.pow(floor.getPlayer().getY() - y, 2)) > 5)
			return false;

		double m = (Controller.get().getPlayer().getY() - y) / (Controller.get().getPlayer().getX() - x);
		double b = y - m*x;
			
		if(x <= (Controller.get().getPlayer().getX())) {
			for(double i = x; i < Controller.get().getPlayer().getX(); i = i + 0.1)
				if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
					return false;
		} else {
			for(double i = x; i > Controller.get().getPlayer().getX(); i = i - 0.1)
				if(floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.WALL || floor.getTiles()[(int)i][(int)(m*i+b)] == TILE.ROCK )
					return false;
		}
		return true;
	}
	
	private void move(double vx, double vy)
	{
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
		
		for(GameObject o : Controller.get().getWorld().getFloor().getObjects()) {
			if(o != this && o instanceof Monster){
				
				double dist = Math.sqrt(Math.pow(o.getX() - x,2) + Math.pow(o.getY() - y,2));
				double limitDist = Math.sqrt(Math.pow(o.getMask().getW()/2 - this.mask.getW()/2 ,2) + Math.pow(o.getMask().getH()/2 - this.mask.getH()/2 ,2));
				if(dist < (limitDist)){
					double angle = Math.atan2(o.getY() - y, o.getX() - x);
					double dp = limitDist - dist;
					double dvx = dp * Math.cos(angle) + 0.01;
					double dvy = dp * Math.sin(angle) + 0.01;
							
					System.out.println(true);
					
					((Monster)o).move(dvx,dvy);
				}
				
				
			}
		}
		
		for(GameObject o : Controller.get().getWorld().getFloor().getObjects()) {
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

	private void moveTo(){
		double angle = getMonsTargetAngle();
		Double vx = SPEED * Math.sin(angle);
		Double vy = SPEED * Math.cos(angle);
		
		move(vx,vy);
	}
	
	private double getMonsTargetAngle(){
		return Math.atan2(nextNode.getCoor().getX() - this.x + 0.5, nextNode.getCoor().getY() - this.y + 0.5);
	}

}
