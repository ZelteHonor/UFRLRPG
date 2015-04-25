package weapons;


import control.Audio;
import control.Controller;
import entity.Monster;
import gameobject.GameObject;
import world.Floor;

public class Sword extends Weapon{
	
	public static final String SWORD_SWOOSH = "sfx/sword_swoosh.wav";
	public static final int startangle = -105;
	public static final int endangle = 50;

	private boolean animate;
	private int anglediff;
	
	public Sword(double x, double y, int damage, int attackspeed) {
		super(x, y, damage, attackspeed);
		sprite = "img/sword.png";
		animate = false;
		anglediff = 0;
	}

	@Override
	public void attack(Floor floor) {
		if (cooldown == 0) {
			animate = true;
			anglediff = startangle;
			cooldown = attackspeed;
			Audio.play("sword_swoosh"+Integer.toString(((int)(Math.random()*3+1))));//TODO
		}
	}
	
	@Override
	public void update(){
		if (animate) {
			anglediff += 15;
			
			for (GameObject o : Controller.get().getObjects())
				if (o instanceof Monster)
					if (Math.sqrt(Math.pow(o.getX() - x, 2) + Math.pow(o.getY() - y, 2)) < 1 && Math.abs(Math.atan2(o.getY()-y,o.getX()-x) - angle) < Math.PI/2)
						((Monster) o).setHealth(((Monster) o).getHealth() - damage / (attackspeed/2));
						//Audio.playSound(10);//TODO
						
		}
		
		if (anglediff >= endangle) {
			animate = false;
			anglediff = 0;
		}
		
		if (cooldown > 0)
			cooldown--;
	}
	
	public int getAngleDiff() {
		return anglediff;
	}
}
