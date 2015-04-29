package gameobject;

import world.Floor;

public abstract class GameObject {

	protected double x;
	protected double y;
	protected double angle;
	protected boolean destroy;

	protected String sprite;
	protected Mask mask;

	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.angle = 0.0;
		destroy = false;
	}
	
	public Mask getMask() {
		return mask;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getAngle() {
		return angle;
	}

	public String getSprite() {
		return sprite;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public boolean isDestroy(){
		return destroy;
	}
	
	public void destroy()
	{
		this.destroy = true;
	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	/**
	 * Explique comment l'objet doit agir dans un game tick.
	 * @param floor
	 * 	Le Floor dans lequel se trouve l'objet.
	 */
	public abstract void update(Floor floor);

}
