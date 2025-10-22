package item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import entity.GameObject;
import game.GameComponent;
import player.Player;

public class Item extends GameObject{
	
	private Animation animSystem;

	private int animScaleX=GameComponent.tileSize,animScaleY=GameComponent.tileSize;

	public Item(int posX,int posY,String patchSheet,int animScaleX,int animScaleY,int lastFrame,int width,int height) {
		super(posX, posY, width, height);

		animSystem = new Animation(patchSheet);
		animSystem.addAnimation("anim-default", lastFrame, 0);
		animSystem.setAnimation("anim-default");
		
		this.animScaleX=animScaleX;
		this.animScaleY=animScaleY;

	}

    public Item(int posX,int posY,String patchSheet,int animScaleX,int animScaleY,int lastFrame,int width,int height, int size_x, int size_y) {
        super(posX, posY, width, height);

        animSystem = new Animation(patchSheet);
        animSystem.addAnimation("anim-default", lastFrame, 0);
        animSystem.setAnimation("anim-default");

        this.animScaleX=animScaleX;
        this.animScaleY=animScaleY;

    }
	
	public Item(int posX,int posY,String patchSheet,int lastFrame) {
		super(posX,posY);

		animSystem = new Animation(patchSheet);
		animSystem.addAnimation("anim-default", lastFrame, 0);
		animSystem.setAnimation("anim-default");
	}
	
	public void draw(Graphics graphic) {
		
		BufferedImage itemImage = animSystem.getImage(animScaleX,animScaleY);// Get image from animation
		graphic.drawImage(itemImage,this.getX(),this.getY(), this.getX()+this.getWidth(),this.getY()+this.getHeight(),0,0, itemImage.getWidth(), itemImage.getHeight(), null);
	}
	
	//Update frame
	public void updateFrame(int time) {
		animSystem.TimerFrame(time);
	}

	public boolean PlayerUse(Player p){return false;}// if player collide with this player will use
}
