package gameObjects;

import entity.Entity;
import world.Floor;

public class Projectile extends GameObjects{
	private int damage, length, vx ,vy;
	
	public Projectile(int damage, int length, int vs, int vy){
		this.damage = damage;
		this.length = length;
		this.vx = vx;
		this.vy = vy;
	}

	@Override
	public void update(Floor floor) {
		boolean exist = true;
		
		for(GameObjects o : floor.getObjects()){
			if(o instanceof Entity && exist && Mask.collide(this.mask, o.mask)){
				((Entity) o).setHealth(((Entity) o).getHealth() - damage);
				floor.getObjects().remove(this);
				exist = false;
			}
		}
		
		for(Mask m : floor.getWalls()){
		if(exist && length <= 0 || Mask.collide(this.mask, m)){
			floor.getObjects().remove(this);
			exist = false;
		}
		else if(exist){
			this.setX(getX() + vx);
			this.setY(getY() + vy);
		}
		
	}
}
