package animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Animation{

	private BufferedImage sheetImage;
	
	private HashMap<String,AnimationStage> animSystem = new HashMap<String,AnimationStage>();
	private AnimationStage currentAnimation;
	
	private int currentFrame=0;
	private byte TimerFrame;
	
	public Animation(String imagePatch) {
		try {
			sheetImage=ImageIO.read(new File(imagePatch));
		}catch(Exception io) {
			JOptionPane.showConfirmDialog(null, "FILE DOSEN'T FOUND:"+imagePatch, null, JOptionPane.OK_OPTION);
			System.exit(-1);
		}
		TimerFrame=0;
	}
	
	public void addAnimation(String type,AnimationStage anim) {
		animSystem.put(type, anim);
	}
	
	public void setAnimation(String type) {
		currentAnimation = animSystem.get(type);
		currentFrame=currentAnimation.getStart();
	}
	
	public void nextFrame() {

		TimerFrame++;
		if(TimerFrame >= 7) {
			
			if(this.currentFrame < this.currentAnimation.getEnd()) {
				this.currentFrame++;
				TimerFrame=0;
				
				return;
			}
			
			currentFrame=currentAnimation.getStart(); //Reset animation
			TimerFrame=0;
		}
		
		return;
	}
	
	public int getFrame() {
		return this.currentFrame;
	}
	
	public BufferedImage getImage() {
		return sheetImage.getSubimage(currentFrame*15, currentAnimation.getPosSheet()*16, 15,15);
	}
}