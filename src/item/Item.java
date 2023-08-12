package item;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import animation.Animation;
import entidy.Entidy;
import game.GameComponent;
import player.Player;

public class Item extends Entidy{
	
	private Animation itemAnim;
	private int animScaleX=GameComponent.tileSize,animScaleY=GameComponent.tileSize;
	
	public Item(int posX,int posY,String patchSheet,int animScaleX,int animScaleY,int lastFrame) {
		super(posX,posY);

		itemAnim = new Animation(patchSheet);
		itemAnim.addAnimation("anim", 0, lastFrame, 0);
		itemAnim.setAnimation("anim");
		
		this.animScaleX=animScaleX;
		this.animScaleY=animScaleY;
	}
	
	public Item(int posX,int posY,String patchSheet,int lastFrame) {
		super(posX,posY);

		itemAnim = new Animation(patchSheet);
		itemAnim.addAnimation("anim", 0, lastFrame, 0);
		itemAnim.setAnimation("anim");
	}
	
	public void draw(Graphics g) {
		
		BufferedImage itemImage = itemAnim.getImage(animScaleX,animScaleY);// Get image from animation
		g.drawImage(itemImage,this.getX(),this.getY(), this.getX()+GameComponent.tileSize/3,this.getY()+GameComponent.tileSize/2,0,0, itemImage.getWidth(), itemImage.getHeight(), null);
	}
	
	//Update frame
	public void updateFrame(int time) {
		itemAnim.TimerFrame(time);
	}
	
	public boolean update(Player p) {return true;}
}
