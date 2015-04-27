package control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import javafx.scene.media.AudioClip;

public class Audio {
	

	private static HashMap<String, AudioClip> sounds;
	

	public static void load() {		
		
		if (Audio.sounds == null)
			sounds = new HashMap<String, AudioClip>();
		
		try {
			Files.walk(Paths.get("sfx")).forEach(filePath -> {
			    if (Files.isRegularFile(filePath))
			        Audio.sounds.put(filePath.getFileName().toString(), new AudioClip(filePath.toUri().toString()));
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void play(String sound){
		play(sound, 1);		
	}
	
	public static void play(String sound, double volume){
		if (!sound.contains(".wav"))
			sound += ".wav";
		
		try
		{
		if(!sounds.get(sound).isPlaying())
			sounds.get(sound).play(volume);			
		}catch(NullPointerException e){}
	}

	public static HashMap<String, AudioClip> getSounds() {
		return sounds;
	}

	public static void setSounds(HashMap<String, AudioClip> sounds) {
		Audio.sounds = sounds;
	}

}
