package control;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import javafx.scene.media.AudioClip;

/**
 * Classe de gestion du son.
 */
public class Audio {

	/**
	 * HashMap de tous les son dans le dossier sfx
	 */
	private static HashMap<String, AudioClip> sounds;

	/**
	 * À partir du répertoire depuis lequel le programme est lancé il charge
	 * tous les fichier audio.
	 */
	public static void load() {

		if (Audio.sounds == null)
			sounds = new HashMap<String, AudioClip>();

		try {
			Files.walk(Paths.get("sfx"))
					.forEach(
							filePath -> {
								if (Files.isRegularFile(filePath))
									Audio.sounds.put(filePath.getFileName()
											.toString(), new AudioClip(filePath
											.toUri().toString()));
							});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Joue le son demandé
	 * 
	 * @param sound
	 *            Nom du son (Nom utilisé dans le fichier)
	 */
	public static void play(String sound) {
		play(sound, 1);
	}

	/**
	 * Joue un son avec un volume donnée.
	 * 
	 * @param sound
	 *            Nom du son (Nom utilisé dans le fichier)
	 * @param volume
	 *            Volume du son
	 */
	public static void play(String sound, double volume) {
		if (!sound.contains(".wav"))
			sound += ".wav";

		try {
			if (!sounds.get(sound).isPlaying())
				sounds.get(sound).play(volume);
		} catch (NullPointerException e) {
		}
	}

}
