package animation;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import game.GameComponent;


public class Animation{

	private BufferedImage sheetImage;
	
	private HashMap<String,AnimationStage> animSystem = new HashMap<String,AnimationStage>();
	private AnimationStage currentAnimation;
	private String state;
	
	private int currentFrame=0;
	private int timerFrame=0;		//Time to charge the frame
	
	//Uses the size of Tile 32 pixels by default
	public Animation(String imagePatch) {
		try {
			sheetImage=ImageIO.read(new File(imagePatch));
		}catch(Exception io) {
			JOptionPane.showConfirmDialog(null, "FILE DOSEN'T FOUND:"+imagePatch, null, JOptionPane.OK_OPTION);
			System.exit(-1);
		}
	}

	public void addAnimation(String nameAnimation,AnimationStage anim) {
		animSystem.put(nameAnimation, anim);
	}
	
	public void addAnimation( String nameAnimation, int startFrame, int endFrame, int posY) {
		animSystem.put(nameAnimation,new AnimationStage(startFrame, endFrame, posY));
	}
	
	
	public void setAnimation(String type) {
		currentAnimation = animSystem.get(type);
		currentFrame=currentAnimation.getStart();
		state=type;
	}
	
	public String getState() {
		return this.state;
	}
	
	
	public boolean nextFrame() {

		if(this.currentFrame < this.currentAnimation.getEnd()) {
			this.currentFrame++;	//Goto next frame
			return true;
		}
			
		currentFrame=currentAnimation.getStart(); //Restart animation
		return false;
	}

	
	public void TimerFrame(int Timer) {

		timerFrame++;
				
		if(timerFrame > Timer) {
			timerFrame=0;
			
			if(currentFrame < currentAnimation.getEnd()) {
				currentFrame++;
				return;
			}
			
			currentFrame=currentAnimation.getStart(); //Reset animation
		}
		
		return;
	}
	
	public int getFrame() {
		return this.currentFrame;
	}

	
	public BufferedImage getImage() {
		return sheetImage.getSubimage(currentFrame*GameComponent.tileSize, currentAnimation.getPosSheet()*GameComponent.tileSize, GameComponent.tileSize,GameComponent.tileSize);
	}
	
	
	public BufferedImage getImage(int scaleX,int scaleY) {
		return sheetImage.getSubimage(currentFrame*scaleX, currentAnimation.getPosSheet()*scaleY, scaleX,scaleY);
	}
}