package control;

import javafx.scene.media.AudioClip;

public class Audio {
	
	public static void playSound(String s){
		
		AudioClip a = new AudioClip(s);
		a.play();
		
	}

}
