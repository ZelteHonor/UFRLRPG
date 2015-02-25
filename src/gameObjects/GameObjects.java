package gameObjects;


public abstract class GameObjects {

	protected int x, y;

	protected int angle;

	protected String sprite;

	public GameObjects() {
		this.x = 0;
		this.y = 0;
		this.angle = 0;

	}
	
	public abstract void update();

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getAngle() {
		return angle;
	}

	public String getSprite() {
		return sprite;
	}

	public void setX(int x) {

		this.x = x;

	}

	public void setY(int y) {
		this.y = y;
	}

	public void setAngle(int nAngle) {

		if (nAngle > 0 && nAngle > 360) {
			this.angle = nAngle;
		}

	}

	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

}
