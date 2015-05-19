package gameobject;

public class Mask {

	public enum SHAPE {
		BOX, CIRCLE
	}

	/**
	 * Forme du collider
	 */
	private SHAPE shape;
	/**
	 * rayon,hauteur,largeur
	 */
	private double r, h, w;
	/**
	 * position (x,y)
	 */
	private double x, y;

	/**
	 * Constructeur
	 * 
	 * @param rayon
	 * @param position
	 *            x
	 * @param position
	 *            y
	 */
	public Mask(double r, double x2, double y2) {
		shape = SHAPE.CIRCLE;
		this.r = r;
		this.x = x2;
		this.y = y2;
	}

	/**
	 * Constructeur
	 * 
	 * @param hauteur
	 * @param largeur
	 * @param position
	 *            x
	 * @param position
	 *            y
	 */
	public Mask(double h, double l, double px, double py) {
		shape = SHAPE.BOX;
		this.h = h;
		this.w = l;
		this.x = px;
		this.y = py;
		this.r = (Math.sqrt(Math.pow(this.h / 2, 2) + Math.pow(this.w / 2, 2)));
	}

	/**
	 * V�rifie s'il y a collision
	 * 
	 * @param a
	 *            , un mask
	 * @param b
	 *            , un mask
	 * @return boolean
	 */
	public static boolean collide(Mask a, Mask b) {
		if (Math.sqrt(Math.pow(a.getX() - b.getX(), 2)
				+ Math.pow(a.getY() - b.getY(), 2)) < a.getR() + b.getR()) {
			if (a.getShape() == SHAPE.CIRCLE && b.getShape() == SHAPE.CIRCLE)
				return true;

			if (a.getShape() == SHAPE.BOX && b.getShape() == SHAPE.BOX) {
				return !((a.getX() - a.getW() / 2 > b.getX() + b.getW() / 2)
						|| (a.getY() - a.getH() / 2 > b.getY() + b.getH() / 2)
						|| (b.getX() - b.getW() / 2 > a.getX() + a.getW() / 2) || (b
						.getY() - b.getH() / 2 > a.getY() + a.getH() / 2));
			}

			if (a.getShape() == SHAPE.CIRCLE)
				return collideCircleBox(a, b);
			return collideCircleBox(b, a);
		}
		return false;
	}

	/**
	 * Check for a collision between a circle mask and a box mask
	 * 
	 * @param a
	 *            The circle mask
	 * @param b
	 *            The box mask
	 * @return boolean
	 */
	private static boolean collideCircleBox(Mask a, Mask b) {
		// Distance x, y entre le cercle et le rectangle (centres)
		double dx = Math.abs(a.getX() - b.getX());
		double dy = Math.abs(a.getY() - b.getY());

		if (dx > b.getW() / 2 + a.getR() || dy > b.getH() / 2 + a.getR())
			return false;

		if (dx <= b.getW() / 2 || dy <= b.getH() / 2)
			return true;

		if (Math.pow(dx - (b.getW() / 2), 2) + Math.pow(dy - (b.getH() / 2), 2) <= Math
				.pow(a.getR(), 2))
			return true;

		return false;
	}

	/**
	 * 
	 * @return shape
	 */
	public SHAPE getShape() {
		return shape;
	}

	/**
	 * 
	 * @return rayon
	 */
	public double getR() {
		return r;
	}

	/**
	 * 
	 * @return hauteur
	 */
	public double getH() {
		return h;
	}

	/**
	 * 
	 * @return largeur
	 */
	public double getW() {
		return w;
	}

	/**
	 * 
	 * @return position x
	 */
	public double getX() {
		return x;
	}

	/**
	 * 
	 * @return position y
	 */
	public double getY() {
		return y;
	}

	/**
	 * 
	 * @param rayon
	 */
	public void setR(double rayon) {
		this.r = rayon;
	}

	/**
	 * 
	 * @param hauteur
	 */
	public void setH(double hauteur) {
		this.h = hauteur;
	}

	/**
	 * 
	 * @param largeur
	 */
	public void setW(double largeur) {
		this.w = largeur;
	}

	/**
	 * 
	 * @param x
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * 
	 * @param y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

}
