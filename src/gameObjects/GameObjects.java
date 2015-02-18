package gameObjects;

import javafx.scene.image.Image;

public abstract class GameObjects {

	private int x, y, angle;
	
	private Image sprite;

	public GameObjects() {
		
		
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAngle() {
		return angle;
	}

	public Image getSprite() {
		return sprite;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAngle(int nAngle) {
		
		if(nAngle > 0 && nAngle > 360)
		{
			this.angle = angle;
		}
		
	}

	public void setSprite(Image sprite) {
		this.sprite = sprite;
	}
	
	

}
