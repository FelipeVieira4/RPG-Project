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
	private byte TimerFrame;		//Time to charge the frame
	
	private Integer scaleX,scaleY;
	
	
	//Uses the size of Tile 32 pixels by default
	public Animation(String imagePatch) {
		try {
			sheetImage=ImageIO.read(new File(imagePatch));
		}catch(Exception io) {
			JOptionPane.showConfirmDialog(null, "FILE DOSEN'T FOUND:"+imagePatch, null, JOptionPane.OK_OPTION);
			System.exit(-1);
		}
		TimerFrame=0;
	}
	
	//Uses the size scaleX and scaleY by default
	public Animation(String imagePatch,int pscaleX,int pscaleY) {
		try {
			sheetImage=ImageIO.read(new File(imagePatch));
		}catch(Exception io) {
			JOptionPane.showConfirmDialog(null, "FILE DOSEN'T FOUND:"+imagePatch, null, JOptionPane.OK_OPTION);
			System.exit(-1);
		}
		
		TimerFrame=0;
		
		scaleX=pscaleX;
		scaleY=pscaleY;
		
	}
	
	public void addAnimation(String type,AnimationStage anim) {
		animSystem.put(type, anim);
	}
	
	public void addAnimation( String type, int start, int end, int pos) {
		animSystem.put(type,new AnimationStage(start, end, pos));
	}
	
	
	public void setAnimation(String type) {
		currentAnimation = animSystem.get(type);
		currentFrame=currentAnimation.getStart();
		state=type;
	}
	
	public String getState() {
		return this.state;
	}
	
	
	public void nextFrame() {

		if(this.currentFrame < this.currentAnimation.getEnd()) {
			this.currentFrame++;	//Goto next frame
			return;
		}
			
		currentFrame=currentAnimation.getStart(); //Restart animation
		return;
	}
	
	public void TimerFrame(int Timer) {

		TimerFrame++;
		if(TimerFrame >= Timer) {
			
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
		
		if(scaleX == null && scaleY == null) {
			return sheetImage.getSubimage(currentFrame*GameComponent.tileSize, currentAnimation.getPosSheet()*GameComponent.tileSize, GameComponent.tileSize,GameComponent.tileSize);
		}
		return sheetImage.getSubimage(currentFrame*scaleX, currentAnimation.getPosSheet()*scaleY, scaleX,scaleY);
	}
}