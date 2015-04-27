package pickups;

import control.Audio;
import world.Floor;

public class Artefact extends PickUps {

	public Artefact(double x, double y) {
		super(x, y);
		sprite ="img/artefact.png";
	}

	@Override
	protected void activateEffect(Floor floor) {
		floor.getPlayer().setArtefact(true);
		Audio.play("artefact_acquired.wav");
	}

}
