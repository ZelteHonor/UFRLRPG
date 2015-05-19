package gameobject;

import world.Floor;

/**
 * Classe de base a tous les objets interactifs sur la carte
 */
public abstract class GameObject {

	/**
	 * Position en X
	 */
	protected double x;
	/**
	 * Position en Y
	 */
	protected double y;
	/**
	 * Angle de l'objet par rapport au 0 trigonométrique
	 */
	protected double angle;
	/**
	 * Tag un objet pour qu'il soit détruit.
	 */
	protected boolean destroy;

	/**
	 * Sprite de l'objet a représenté
	 */
	protected String sprite;
	/**
	 * Mask de collision
	 */
	protected Mask mask;

	/**
	 * Constructeur générique d'objet
	 * 
	 * @param x
	 *            Origine en X
	 * @param y
	 *            Origine en Y
	 */
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.angle = 0.0;
		destroy = false;
	}

	/**
	 * Retourne le mask de l'objet
	 * 
	 * @return Le mask
	 */
	public Mask getMask() {
		return mask;
	}

	/**
	 * Retourne la position en X
	 * 
	 * @return La position en X
	 */
	public double getX() {
		return x;
	}

	/**
	 * Retourne la position en Y
	 * 
	 * @return La position en Y
	 */
	public double getY() {
		return y;
	}

	/**
	 * Retourne l'angle pour le 0 trigonométrique
	 * 
	 * @return L'angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Retourne le sprite
	 * 
	 * @return Le sprite
	 */
	public String getSprite() {
		return sprite;
	}

	/**
	 * Change la position en X
	 * 
	 * @param x
	 *            La nouvelle position en X
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Change la position en Y
	 * 
	 * @param y
	 *            La nouvelle position en Y
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Change la position X et Y en même temps
	 * 
	 * @param x
	 *            La nouvelle position en X
	 * @param y
	 *            La nouvelle position en Y
	 */
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Change l'angle
	 * 
	 * @param angle
	 *            Le nouvel angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Vérifie si l'objet doit être détruit
	 * 
	 * @return Vrai s'il doit être détruit.
	 */
	public boolean isDestroy() {
		return destroy;
	}

	/**
	 * Tag l'objet pour qu'il doit détruit
	 */
	public void destroy() {
		this.destroy = true;
	}

	/**
	 * Change le sprite de l'objet
	 * 
	 * @param sprite
	 *            Le nouveau sprite
	 */
	public void setSprite(String sprite) {
		this.sprite = sprite;
	}

	/**
	 * Explique comment l'objet doit agir dans un game tick.
	 * 
	 * @param floor
	 *            Le Floor dans lequel se trouve l'objet.
	 */
	public abstract void update(Floor floor);

}
