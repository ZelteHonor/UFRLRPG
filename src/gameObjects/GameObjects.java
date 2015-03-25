package gameObjects;

import world.Floor;
import javafx.scene.image.Image;

public abstract class GameObjects {

	protected double x;
	protected double y;
	protected double angle;

	protected String sprite;

	public GameObjects() {
		this.x = 0;
		this.y = 0;
		this.angle = 0.0;

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

	public void setAngle(double nAngle) {

		this.angle = 360*((nAngle/360) - (Math.toIntExact((long) (nAngle/360))));

	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}
	
	public abstract void update(Floor floor);

}
