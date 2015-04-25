package control;

import java.util.ArrayList;

import javafx.scene.media.AudioClip;

public class Audio {
	

	private static ArrayList<AudioClip> sounds;
	
	public static void playSound(String i){
		
		for(AudioClip a : sounds)
		{
			if(a.getSource().contains(i))
			{
				a.play();
			}
		}
		
		
	}

	public static ArrayList<AudioClip> getSounds() {
		return sounds;
	}

	public static void setSounds(ArrayList<AudioClip> sounds) {
		Audio.sounds = sounds;
	}

}
