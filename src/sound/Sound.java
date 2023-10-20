package sound;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

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
			JOptionPane.showMessageDialog(null, "sound:"+patch+" dosen't found or format not surpported",null,JOptionPane.CLOSED_OPTION);
			System.exit(-1);
			return false;
		}
		
		
		return true;
	}
	
	public void play() {
		clip.start();
	}
	
	public void loop() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void close() {
		clip.close();
	}
}
