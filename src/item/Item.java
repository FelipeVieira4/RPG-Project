package item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import entidy.GameObject;
import game.GameComponent;
import player.Player;

public class Item extends GameObject{
	
	private Animation animSystem;

	private int animScaleX=GameComponent.tileSize,animScaleY=GameComponent.tileSize;
	

	public Item(int posX,int posY,String patchSheet,int animScaleX,int animScaleY,int lastFrame) {
		super(posX,posY);

		animSystem = new Animation(patchSheet);
		animSystem.addAnimation("anim-default", 0, lastFrame, 0);
		animSystem.setAnimation("anim-default");
		
		this.animScaleX=animScaleX;
		this.animScaleY=animScaleY;
	}
	
	public Item(int posX,int posY,String patchSheet,int lastFrame) {
		super(posX,posY);

		animSystem = new Animation(patchSheet);
		animSystem.addAnimation("anim-default", 0, lastFrame, 0);
		animSystem.setAnimation("anim-default");
	}
	
	public void draw(Graphics graphic) {
		
		BufferedImage itemImage = animSystem.getImage(animScaleX,animScaleY);// Get image from animation
		graphic.drawImage(itemImage,this.getX(),this.getY(), this.getX()+GameComponent.tileSize/3,this.getY()+GameComponent.tileSize/2,0,0, itemImage.getWidth(), itemImage.getHeight(), null);
	}
	
	//Update frame
	public void updateFrame(int time) {
		animSystem.TimerFrame(time);
	}

	public boolean CanPlayerUse(Player p){return false;}// if player colide with this player will use
}
