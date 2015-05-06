package pickups;

import control.Audio;
import control.Controller;
import world.Floor;

public class Artefact extends PickUps {

	public Artefact(double x, double y) {
		super(x, y);
		sprite ="img/artefact.png";
	}

	@Override
	protected void activateEffect(Floor floor) {
		floor.getPlayer().setArtefact(true);
		Controller.get().getWorld().regenMonster();
		Audio.play("artefact"+Integer.toString((int)(Math.random()*4+1)));
	}

}
