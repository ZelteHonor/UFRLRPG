package gameObjects;

public class Mask {
	
	public enum SHAPE {BOX, CIRCLE}
	
	private SHAPE shape;
	private int r, h, w;
	private int x, y;
	
	public Mask(int r, int px, int py)
	{
		shape = SHAPE.CIRCLE;
		this.r = r;
		this.x = px;
		this.y = py;
	}
	
	public Mask (int h, int l, int px, int py)
	{
		shape = SHAPE.BOX;
		this.h = h;
		this.w = l;
		this.x = px;
		this.y = py;
		this.r = (int)(Math.sqrt(Math.pow(this.h/2, 2)+Math.pow(this.w/2,2)));
	}

	public int getR() {
		return r;
	}

	public int getH() {
		return h;
	}

	public int getW() {
		return w;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setR(int rayon) {
		this.r = rayon;
	}

	public void setH(int hauteur) {
		this.h = hauteur;
	}

	public void setW(int largeur) {
		this.w = largeur;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

}
