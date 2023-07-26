package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

public class Sound{

	Clip clip;
	
	public Sound(String patch) {
		this.loadMusic(patch);
	}
	
	public boolean loadMusic(String patch) {
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(patch));

			Clip pclip = AudioSystem.getClip();
			pclip.open(audioStream);
			
			this.clip=pclip;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
			return false;
		}
		
		
		return true;
	}
	
	public void play() {
		clip.start();
	}
	
	public void close() {
		clip.close();
	}
}
