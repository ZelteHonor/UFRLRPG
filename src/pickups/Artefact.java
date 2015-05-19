package pickups;

import control.Audio;
import control.Controller;
import world.Floor;

/**
 * Classe qui représente l'artefact sur la carte.
 */
public class Artefact extends PickUps {

	/**
	 * Constructeur de l'objet artefact
	 * @param x
	 * 	Position en X
	 * @param y
	 * 	Position en Y
	 */
	public Artefact(double x, double y) {
		super(x, y);
		sprite ="img/artefact.png";
	}

	/** 
	 * Joue un son lorsque l'artefact est récupérer
	 */
	@Override
	protected void activateEffect(Floor floor) {
		floor.getPlayer().setArtefact(true);
		Controller.get().getWorld().regenMonster();
		Audio.play("artefact"+Integer.toString((int)(Math.random()*4+1)));
	}

}
